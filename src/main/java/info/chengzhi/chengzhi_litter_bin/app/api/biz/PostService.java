package info.chengzhi.chengzhi_litter_bin.app.api.biz;

import info.chengzhi.chengzhi_litter_bin.app.infra.persistence.sql.model.LitterBinPost;

public interface PostService {
  LitterBinPost getPostById(Long postId);

  void addPost(LitterBinPost post) throws Exception;
}
