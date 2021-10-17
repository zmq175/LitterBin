package info.chengzhi.chengzhi_litter_bin.app.api.biz.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import info.chengzhi.chengzhi_litter_bin.app.api.biz.PostService;
import info.chengzhi.chengzhi_litter_bin.app.api.biz.response.Post;
import info.chengzhi.chengzhi_litter_bin.app.api.biz.response.Tag;
import info.chengzhi.chengzhi_litter_bin.app.domain.exceptions.CreatePostTooFastException;
import info.chengzhi.chengzhi_litter_bin.app.domain.exceptions.PostContentEmptyException;
import info.chengzhi.chengzhi_litter_bin.app.domain.exceptions.PostNotExistException;
import info.chengzhi.chengzhi_litter_bin.app.domain.exceptions.TicketCodeNotExistException;
import info.chengzhi.chengzhi_litter_bin.app.domain.services.core.LitterBinPostService;
import info.chengzhi.chengzhi_litter_bin.app.domain.services.core.LitterBinTagService;
import info.chengzhi.chengzhi_litter_bin.app.domain.utils.ThreadLocalUtils;
import info.chengzhi.chengzhi_litter_bin.app.infra.persistence.sql.model.LitterBinPost;
import info.chengzhi.chengzhi_litter_bin.app.infra.persistence.sql.model.LitterBinPostTagMapping;
import info.chengzhi.chengzhi_litter_bin.app.infra.persistence.sql.model.LitterBinTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
  private static Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);

  @Autowired
  private LitterBinPostService litterBinPostService;

  @Autowired
  private LitterBinTagService litterBinTagService;

  @Override
  public Post getPostById(Long postId) throws PostNotExistException {
    LitterBinPost litterBinPost = litterBinPostService.getPostById(postId);
    LOGGER.info("get litter bin post: {}", JSON.toJSONString(litterBinPost));
    if (Objects.isNull(litterBinPost)) {
      throw new PostNotExistException("post not exist.");
    }
    Post post = new Post();
    post.setPostId(litterBinPost.getPostId());
    post.setContent(litterBinPost.getContent());
    post.setCreateTime(litterBinPost.getCreateTime());
    post.setUpdateTime(litterBinPost.getUpdateTime());
    post.setExpireTime(litterBinPost.getExpireTime());
    post.setCreator(litterBinPost.getCreator());
    List<LitterBinTag> litterBinTags = litterBinTagService.getTagsByPostId(postId);
    List<Tag> tags = new ArrayList<>();
    for (LitterBinTag litterBinTag : litterBinTags) {
      Tag tag = new Tag();
      tag.setTagId(litterBinTag.getTagId());
      tag.setTagName(litterBinTag.getTagName());
      tags.add(tag);
    }
    post.setTags(tags);
    return post;
  }

  @Override
  public void addPost(Post post) throws Exception {
    String ticketCode = ThreadLocalUtils.getTicketCode();
    if (Strings.isNullOrEmpty(ticketCode)) {
      throw new TicketCodeNotExistException("ticket code not exist");
    }
    if (Strings.isNullOrEmpty(post.getContent())) {
      throw new PostContentEmptyException("content cannot be null");
    }
    LitterBinPost latestPost = litterBinPostService.getLatestPostByCreator(ticketCode);
    if (Objects.nonNull(latestPost)) {
      LocalDateTime lastPostTime = latestPost
          .getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
      if (LocalDateTime.now().isBefore(lastPostTime.plusSeconds(5))) {
        throw new CreatePostTooFastException("create post too fast");
      }
    }
    LitterBinPost litterBinPost = new LitterBinPost();
    litterBinPost.setCreator(ticketCode);
    litterBinPost.setContent(post.getContent());
    litterBinPost.setExpireTime(post.getExpireTime());
    litterBinPostService.addPost(litterBinPost);

    List<Tag> tags = post.getTags();
    if (!CollectionUtils.isEmpty(tags)) {
      List<String> tagNames = tags
          .stream()
          .map(Tag::getTagName)
          .collect(Collectors.toList());
      List<LitterBinTag> existTags = litterBinTagService.getTagsByTagNames(tagNames);
      List<String> existTagNames = existTags
          .stream()
          .map(LitterBinTag::getTagName)
          .collect(Collectors.toList());
      List<LitterBinTag> tagsToAdd = tags
          .stream()
          .filter(tag -> !existTagNames.contains(tag.getTagName()))
          .map(tag ->{
            LitterBinTag litterBinTag = new LitterBinTag();
            litterBinTag.setTagName(tag.getTagName());
            litterBinTag.setCreator(ticketCode);
            return litterBinTag;
          })
          .collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(tagsToAdd)) {
        litterBinTagService.addTags(tagsToAdd);
      }
      List<LitterBinPostTagMapping> litterBinPostTagMappings = new ArrayList<>();
      if (!CollectionUtils.isEmpty(existTags)) {
        for (LitterBinTag existTag : existTags) {
          LitterBinPostTagMapping tmp = new LitterBinPostTagMapping();
          tmp.setPostId(litterBinPost.getPostId());
          tmp.setTagId(existTag.getTagId());
          litterBinPostTagMappings.add(tmp);
        }
      }
      if (!CollectionUtils.isEmpty(tagsToAdd)) {
        for (LitterBinTag addTag : tagsToAdd) {
          LitterBinPostTagMapping tmp = new LitterBinPostTagMapping();
          tmp.setPostId(litterBinPost.getPostId());
          tmp.setTagId(addTag.getTagId());
          litterBinPostTagMappings.add(tmp);
        }
      }
      if (!CollectionUtils.isEmpty(litterBinPostTagMappings)) {
        litterBinTagService.addMappings(litterBinPostTagMappings);
      }
    }
  }


}
