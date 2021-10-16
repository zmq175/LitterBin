package info.chengzhi.chengzhi_litter_bin.app.domain.exceptions;

public class LitterBinInternalException extends Exception {
  public LitterBinInternalException(String msg) {
    super(msg);
  }

  public LitterBinInternalException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
