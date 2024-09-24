package by.ruslan.project.configuration;

import by.ruslan.project.config.WithMockJwtAuth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockJwtAuthSecurityContextFactory implements WithSecurityContextFactory<WithMockJwtAuth> {

  @Override
  public SecurityContext createSecurityContext(WithMockJwtAuth withMockJwtAuth) {
    List<SimpleGrantedAuthority> authorities = List.of(withMockJwtAuth.roles())
        .stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    Jwt jwt = Jwt.withTokenValue("mock-token")
        .claim("realm_access", Map.of("roles", List.of(withMockJwtAuth.roles())))
        .header("alg", "none")
        .build();

    JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(jwt, authorities);
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(jwtAuthenticationToken);
    return context;
  }
}
