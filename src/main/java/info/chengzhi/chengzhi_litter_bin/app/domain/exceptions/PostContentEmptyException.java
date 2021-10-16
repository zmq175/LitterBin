package info.chengzhi.chengzhi_litter_bin.app.domain.exceptions;

public class PostContentEmptyException extends LitterBinInternalException {
  public PostContentEmptyException(String msg) {
    super(msg);
  }

  public PostContentEmptyException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
