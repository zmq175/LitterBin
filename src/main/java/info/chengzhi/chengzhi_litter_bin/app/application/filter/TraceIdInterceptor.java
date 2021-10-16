package info.chengzhi.chengzhi_litter_bin.app.application.filter;

import info.chengzhi.chengzhi_litter_bin.app.domain.utils.ThreadLocalUtils;
import info.chengzhi.chengzhi_litter_bin.app.domain.utils.TraceIdUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TraceIdInterceptor implements HandlerInterceptor {
  private static final String TRACE_ID = "trace";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    MDC.put(TRACE_ID, TraceIdUtils.genTraceId());
    ThreadLocalUtils.setTraceId(MDC.get(TRACE_ID));
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
    MDC.clear();
    ThreadLocalUtils.setTraceId("");
  }
}
