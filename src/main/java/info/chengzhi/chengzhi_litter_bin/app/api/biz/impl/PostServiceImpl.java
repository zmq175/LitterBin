package info.chengzhi.chengzhi_litter_bin.app.api.biz.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import info.chengzhi.chengzhi_litter_bin.app.api.biz.PostService;
import info.chengzhi.chengzhi_litter_bin.app.domain.exceptions.CreatePostTooFastException;
import info.chengzhi.chengzhi_litter_bin.app.domain.exceptions.PostContentEmptyException;
import info.chengzhi.chengzhi_litter_bin.app.domain.exceptions.TicketCodeNotExistException;
import info.chengzhi.chengzhi_litter_bin.app.domain.services.core.LitterBinPostService;
import info.chengzhi.chengzhi_litter_bin.app.domain.utils.ThreadLocalUtils;
import info.chengzhi.chengzhi_litter_bin.app.infra.persistence.sql.model.LitterBinPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Service
public class PostServiceImpl implements PostService {
  private static Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);

  @Autowired
  private LitterBinPostService litterBinPostService;

  @Override
  public LitterBinPost getPostById(Long postId) {
    LitterBinPost litterBinPost = litterBinPostService.getPostById(postId);
    LOGGER.info("get litter bin post: {}", JSON.toJSONString(litterBinPost));
    return litterBinPost;
  }

  @Override
  public void addPost(LitterBinPost post) throws Exception {
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
    post.setCreator(ticketCode);
    litterBinPostService.addPost(post);
  }


}
