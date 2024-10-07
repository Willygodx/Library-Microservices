package by.ruslan.freebooksservice.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientFilterConfig {

  @Bean
  public FilterRegistrationBean<FeignClientFilter> loggingFilter(){
    FilterRegistrationBean<FeignClientFilter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter(new FeignClientFilter());
    registrationBean.addUrlPatterns("/free-books/*");

    return registrationBean;
  }
}

