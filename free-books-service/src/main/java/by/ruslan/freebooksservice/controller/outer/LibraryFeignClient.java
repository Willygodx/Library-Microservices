package by.ruslan.freebooksservice.controller.outer;

import by.ruslan.freebooksservice.configuration.FeignClientConfig;
import by.ruslan.freebooksservice.dto.BookDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "library-api-service", configuration = FeignClientConfig.class)
public interface LibraryFeignClient {

  @GetMapping("library/books/{isbns}")
  List<BookDto> getBooksByIsbns(@PathVariable List<String> isbns);

}
