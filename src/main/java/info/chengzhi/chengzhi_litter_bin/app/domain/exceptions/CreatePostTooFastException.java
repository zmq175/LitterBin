package info.chengzhi.chengzhi_litter_bin.app.domain.exceptions;

public class CreatePostTooFastException extends LitterBinInternalException {
  public CreatePostTooFastException(String msg) {
    super(msg);
  }

  public CreatePostTooFastException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
