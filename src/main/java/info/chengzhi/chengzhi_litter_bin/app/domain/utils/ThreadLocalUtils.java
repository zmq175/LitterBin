package info.chengzhi.chengzhi_litter_bin.app.domain.utils;

public class ThreadLocalUtils {
  private static final ThreadLocal<String> TRACE_ID_THREAD_LOCAL = new ThreadLocal<>();

  private static final ThreadLocal<String> TICKET_CODE_THREAD_LOCAL = new ThreadLocal<>();

  public static String getTraceId() {
    return TRACE_ID_THREAD_LOCAL.get();
  }

  public static void setTraceId(String traceIdStr) {
    TRACE_ID_THREAD_LOCAL.set(traceIdStr);
  }

  public static String getTicketCode() {
    return TICKET_CODE_THREAD_LOCAL.get();
  }

  public static void setTicketCode(String ticketCode) {
    TICKET_CODE_THREAD_LOCAL.set(ticketCode);
  }
}
