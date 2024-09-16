package by.ruslan.project.configuration;

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
public class FeignClientConfig {

  @Bean
  public RequestInterceptor requestInterceptor() {
    return new FeignClientInterceptor();
  }
}
