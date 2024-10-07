package by.ruslan.freebooksservice.controller.outer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import by.ruslan.freebooksservice.dto.BookDto;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@Import(LibraryFeignClientTest.Config.class)
class LibraryFeignClientTest {

  @Mock
  private LibraryFeignClient libraryFeignClient;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetBooksByIsbns_Success() {
    List<String> isbns = Arrays.asList("978-3-16-148410-0", "978-1-4028-9462-6");
    BookDto book1 = new BookDto("978-3-16-148410-0", "Book 1", "Genre 1", "Description 1", "Author 1");
    BookDto book2 = new BookDto("978-1-4028-9462-6", "Book 2", "Genre 2", "Description 2", "Author 2");
    List<BookDto> mockResponse = Arrays.asList(book1, book2);

    when(libraryFeignClient.getBooksByIsbns(isbns)).thenReturn(mockResponse);

    List<BookDto> result = libraryFeignClient.getBooksByIsbns(isbns);
    assertEquals(2, result.size());
    assertEquals("Book 1", result.get(0).getName());
    assertEquals("Book 2", result.get(1).getName());
  }

  @Test
  void testGetBooksByIsbns_EmptyIsbnsList() {
    List<String> isbns = Collections.emptyList();

    when(libraryFeignClient.getBooksByIsbns(isbns)).thenReturn(Collections.emptyList());

    List<BookDto> result = libraryFeignClient.getBooksByIsbns(isbns);
    assertEquals(0, result.size());
  }

  @Test
  void testGetBooksByIsbns_InvalidIsbn() {
    List<String> isbns = Arrays.asList("invalid-isbn");

    when(libraryFeignClient.getBooksByIsbns(isbns)).thenReturn(Collections.emptyList());

    List<BookDto> result = libraryFeignClient.getBooksByIsbns(isbns);
    assertEquals(0, result.size());
  }

  @Test
  void testGetBooksByIsbns_ThrowsException() {
    List<String> isbns = Arrays.asList("978-3-16-148410-0");

    when(libraryFeignClient.getBooksByIsbns(isbns)).thenThrow(new RuntimeException("Service unavailable"));

    try {
      libraryFeignClient.getBooksByIsbns(isbns);
    } catch (Exception e) {
      assertEquals("Service unavailable", e.getMessage());
    }
  }

  @TestConfiguration
  static class Config {
    @Bean
    public LibraryFeignClient libraryFeignClient() {
      return Mockito.mock(LibraryFeignClient.class);
    }
  }
}
