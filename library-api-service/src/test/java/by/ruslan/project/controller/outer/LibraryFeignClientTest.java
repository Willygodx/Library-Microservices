package by.ruslan.project.controller.outer;

import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@Import(LibraryFeignClientTest.Config.class)
class LibraryFeignClientTest {

  @MockBean
  private LibraryFeignClient libraryFeignClient;

  @Test
  void testAddBook() {
    String isbn = "978-3-16-148410-0";
    libraryFeignClient.addBook(isbn);
    Mockito.verify(libraryFeignClient).addBook(isbn);
  }

  @Test
  void testDeleteBook() {
    String isbn = "978-3-16-148410-0";
    libraryFeignClient.deleteBook(isbn);
    Mockito.verify(libraryFeignClient).deleteBook(isbn);
  }

  @Test
  void testAddBookWithLongIsbn() {
    String longIsbn = "978-3-16-148410-012345678901234567890";
    libraryFeignClient.addBook(longIsbn);
    Mockito.verify(libraryFeignClient).addBook(longIsbn);
  }

  @Test
  void testDeleteBookWithLongIsbn() {
    String longIsbn = "978-3-16-148410-012345678901234567890";
    libraryFeignClient.deleteBook(longIsbn);
    Mockito.verify(libraryFeignClient).deleteBook(longIsbn);
  }

  @Test
  void testAddBookFeignException() {
    String isbn = "978-3-16-148410-0";
    Mockito.doThrow(FeignException.class).when(libraryFeignClient).addBook(isbn);

    Assertions.assertThrows(FeignException.class, () -> {
      libraryFeignClient.addBook(isbn);
    });
  }

  @Test
  void testDeleteBookFeignException() {
    String isbn = "978-3-16-148410-0";
    Mockito.doThrow(FeignException.class).when(libraryFeignClient).deleteBook(isbn);

    Assertions.assertThrows(FeignException.class, () -> {
      libraryFeignClient.deleteBook(isbn);
    });
  }

  @TestConfiguration
  static class Config {
    @Bean
    public LibraryFeignClient libraryFeignClient() {
      return Mockito.mock(LibraryFeignClient.class);
    }
  }
}
