package by.ruslan.project.config;

import by.ruslan.project.configuration.WithMockJwtAuthSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockJwtAuthSecurityContextFactory.class)
public @interface WithMockJwtAuth {
  String[] roles() default {};
}
