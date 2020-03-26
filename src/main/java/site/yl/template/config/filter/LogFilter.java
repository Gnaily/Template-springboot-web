package site.yl.template.config.filter;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


public class LogFilter  extends OncePerRequestFilter {
  private final Logger log= LoggerFactory.getLogger(LogFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    ThreadContext.put("id", UUID.randomUUID().toString());
    StopWatch timer = new StopWatch();
    try {
      timer.start();
      chain.doFilter(request, response);
    } finally {
      timer.stop();
      writeApplicationLog(request,response,timer);
    }

  }

  private void writeApplicationLog(HttpServletRequest request, HttpServletResponse response, StopWatch timer) {
    if(log.isDebugEnabled()) {
         String length = response.getHeader("Content-Length");
         log.debug("request  [from ip:{},protocol={},method={},sessionId={},uri:{},,request param:{}]",
          request.getRemoteAddr(),
          request.getProtocol(),
          request.getMethod(), request.getRequestedSessionId(), request.getRequestURI());
      log.debug("response [status:{},length={},time={}]", response.getStatus(), length, timer);
    }
    //------------------------------------------

  }

}
