package info.chengzhi.chengzhi_litter_bin.app.domain.exceptions;

public class TicketCodeNotExistException extends LitterBinInternalException {
  public TicketCodeNotExistException(String msg) {
    super(msg);
  }

  public TicketCodeNotExistException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
