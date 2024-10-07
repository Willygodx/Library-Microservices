package by.ruslan.freebooksservice.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FeignClientFilter extends HttpFilter {

  private static final String FEIGN_CLIENT_HEADER = "X-Feign-Client";

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String method = request.getMethod();
    String path = request.getRequestURI();

    if (("POST".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method)) && path.startsWith("/free-books")) {
      String feignClientHeader = request.getHeader(FEIGN_CLIENT_HEADER);
      if (feignClientHeader == null || !feignClientHeader.equals("true")) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        return;
      }
    }
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {}
}
