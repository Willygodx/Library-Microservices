package by.ruslan.freebooksservice.controller.outer;

import by.ruslan.freebooksservice.configuration.FeignClientConfig;
import by.ruslan.freebooksservice.dto.BookDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "library-api-service", configuration = FeignClientConfig.class)
public interface LibraryFeignClient {

  @PostMapping("library/books/by-ids")
  List<BookDto> getBooksByIds(@RequestBody List<Long> ids);

}
