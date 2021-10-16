package info.chengzhi.chengzhi_litter_bin.app.application.filter;

import com.google.common.base.Strings;
import info.chengzhi.chengzhi_litter_bin.app.domain.constant.Dict;
import info.chengzhi.chengzhi_litter_bin.app.domain.utils.ThreadLocalUtils;
import info.chengzhi.chengzhi_litter_bin.app.domain.utils.TicketCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class TicketCodeInterceptor implements HandlerInterceptor {
  private static final Logger LOGGER = LoggerFactory.getLogger(TicketCodeInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String ticketCode = (String) request.getSession().getAttribute(Dict.ticketCode);
    if (Objects.isNull(ticketCode)) {
      Cookie[] cookies = request.getCookies();
      String cookieTicketCode = "";
      if (Objects.nonNull(cookies)) {
        for (Cookie cookie : cookies) {
          if (Dict.ticketCode.equals(cookie.getName())) {
            LOGGER.info("found ticket code in cookie, ticket code: {}", cookie.getValue());
            cookieTicketCode = cookie.getValue();
            Cookie ticket = new Cookie(Dict.ticketCode, cookieTicketCode);
            ticket.setMaxAge(2592000);
            response.addCookie(ticket);
          }
        }
        if (!Strings.isNullOrEmpty(cookieTicketCode)) {
          request.getSession().setAttribute(Dict.ticketCode, cookieTicketCode);
          ThreadLocalUtils.setTicketCode(cookieTicketCode);
        }
      } else {
        ticketCode = TicketCodeUtils.genTicketCode();
        request.getSession().setAttribute(Dict.ticketCode, ticketCode);
        Cookie ticket = new Cookie(Dict.ticketCode, ticketCode);
        ticket.setMaxAge(2592000);
        response.addCookie(ticket);
        ThreadLocalUtils.setTicketCode(ticketCode);
        LOGGER.info("no ticket code in cookie, set ticket code: {}", ticketCode);
      }
    }
    return true;
  }
}
