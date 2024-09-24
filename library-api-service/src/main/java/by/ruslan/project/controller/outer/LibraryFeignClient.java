package by.ruslan.project.controller.outer;

import by.ruslan.project.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "free-books-service", configuration = FeignClientConfig.class)
public interface LibraryFeignClient {

  @PostMapping("/free-books")
  void addBook(@RequestParam("bookId") Long bookId);

  @DeleteMapping("/free-books")
  void deleteBook(@RequestParam("bookId") Long bookId);

}
