package by.ruslan.project.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

  private static final String FEIGN_CLIENT_HEADER = "X-Feign-Client";

  @Override
  public void apply(RequestTemplate template) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
      Jwt jwt = (Jwt) authentication.getPrincipal();
      template.header("Authorization", "Bearer " + jwt.getTokenValue());
    }
    template.header(FEIGN_CLIENT_HEADER, "true");
  }
}
