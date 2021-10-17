package info.chengzhi.chengzhi_litter_bin.app.domain.exceptions;

public class PostNotExistException extends LitterBinInternalException{
  public PostNotExistException(String msg) {
    super(msg);
  }

  public PostNotExistException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
