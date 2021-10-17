package info.chengzhi.chengzhi_litter_bin.app.api.biz;

import info.chengzhi.chengzhi_litter_bin.app.api.biz.response.Post;
import info.chengzhi.chengzhi_litter_bin.app.domain.exceptions.PostNotExistException;

public interface PostService {
  Post getPostById(Long postId) throws PostNotExistException;

  void addPost(Post post) throws Exception;
}
