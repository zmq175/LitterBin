package info.chengzhi.chengzhi_litter_bin.app.domain.utils;

import org.slf4j.MDC;

import java.util.Objects;
import java.util.UUID;

public class TraceIdUtils {
  public static String genTraceId() {
    String uuid = UUID.randomUUID().toString();
    return uuid.replace("-", "");
  }

  public static String getTraceId() {
    String traceId = MDC.get("trace");
    if (Objects.isNull(traceId)) {
      return genTraceId();
    }
    return traceId;
  }
}
