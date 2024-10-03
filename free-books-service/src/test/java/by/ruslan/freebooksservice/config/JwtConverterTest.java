package by.ruslan.freebooksservice.config;

import by.ruslan.freebooksservice.configuration.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JwtConverterTest {

  @Test
  public void testJwtConverter() {
    SecurityConfig securityConfig = new SecurityConfig();
    JwtAuthenticationConverter converter = securityConfig.jwtConverter();

    Jwt jwt = Jwt.withTokenValue("token")
        .header("alg", "none")
        .claim("preferred_username", "testuser")
        .claim("realm_access", Map.of("roles", List.of("ROLE_USER", "ROLE_LIBRARIAN")))
        .build();

    Collection<GrantedAuthority> authorities = converter.convert(jwt).getAuthorities();

    assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_LIBRARIAN")));
  }
}
