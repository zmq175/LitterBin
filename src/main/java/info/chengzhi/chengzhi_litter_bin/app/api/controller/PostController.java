package info.chengzhi.chengzhi_litter_bin.app.api.controller;

import com.alibaba.fastjson.JSON;
import info.chengzhi.chengzhi_litter_bin.app.api.biz.PostService;
import info.chengzhi.chengzhi_litter_bin.app.api.biz.response.Post;
import info.chengzhi.chengzhi_litter_bin.app.api.response.Responses;
import info.chengzhi.chengzhi_litter_bin.app.infra.persistence.sql.model.LitterBinPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController
@RequestMapping("/post")
public class PostController {
  private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

  @Autowired
  private PostService postService;

  @RequestMapping("/getPostById")
  public Object getPostById(HttpServletRequest request,
                            @RequestParam("postId") Long postId) {
    try {
      Post post = postService.getPostById(postId);
      return Responses.successResponse(request, post);
    } catch (Exception e) {
      LOGGER.error("get post error, {}", e.getMessage());
      Arrays.stream(e.getStackTrace()).forEach(r -> LOGGER.error(r.toString()));
      return Responses.errorResponse(request, e);
    }
  }

  @RequestMapping("/addPost")
  public Object addPost(HttpServletRequest request,
                        @RequestBody LitterBinPost post) {
    try {
      LOGGER.info("post to add:{}", JSON.toJSONString(post));
      postService.addPost(post);
      return Responses.successResponse(request);
    } catch (Exception e) {
      LOGGER.error("add post error, {}", e.getMessage());
      Arrays.stream(e.getStackTrace()).forEach(r -> LOGGER.error(r.toString()));
      return Responses.errorResponse(request, e);
    }
  }
}
