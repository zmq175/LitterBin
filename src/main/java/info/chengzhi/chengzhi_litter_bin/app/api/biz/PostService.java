package info.chengzhi.chengzhi_litter_bin.app.api.biz;

import info.chengzhi.chengzhi_litter_bin.app.api.biz.response.Post;
import info.chengzhi.chengzhi_litter_bin.app.domain.exceptions.PostNotExistException;
import info.chengzhi.chengzhi_litter_bin.app.infra.persistence.sql.model.LitterBinPost;

public interface PostService {
  Post getPostById(Long postId) throws PostNotExistException;

  void addPost(LitterBinPost post) throws Exception;
}
