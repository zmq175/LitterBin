package info.chengzhi.chengzhi_litter_bin.app.domain.services.core;

import info.chengzhi.chengzhi_litter_bin.app.infra.persistence.mapper.LitterBinPostMapper;
import info.chengzhi.chengzhi_litter_bin.app.infra.persistence.sql.model.LitterBinPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LitterBinPostService {
  private static final Logger LOGGER = LoggerFactory.getLogger(LitterBinPostService.class);

  @Autowired
  private LitterBinPostMapper litterBinPostMapper;

  public LitterBinPost getPostById(Long postId) {
    return litterBinPostMapper.selectByPrimaryKey(postId);
  }

  public LitterBinPost getLatestPostByCreator(String creator) {
    return litterBinPostMapper.selectLatestPostByCreator(creator);
  }

  public void addPost(LitterBinPost litterBinPost) {
    litterBinPostMapper.insertSelective(litterBinPost);
  }
}
