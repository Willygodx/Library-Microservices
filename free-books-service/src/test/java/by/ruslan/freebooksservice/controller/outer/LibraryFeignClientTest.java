package by.ruslan.freebooksservice.controller.outer;

import by.ruslan.freebooksservice.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class LibraryFeignClientTest {

  @Autowired
  private LibraryFeignClient libraryFeignClient;

  @MockBean
  private LibraryFeignClient libraryFeignClientMock;

  @Test
  public void testGetBooksByIsbns() {
    List<String> isbns = List.of("978-0-399-15779-1", "978-0-399-15779-2");
    List<BookDto> expectedBooks = List.of(
        new BookDto("1234567890", "Book1", "Genre1", "Description1", "Author1"),
        new BookDto("0987654321", "Book2", "Genre2", "Description2", "Author2")
    );

    when(libraryFeignClientMock.getBooksByIsbns(isbns)).thenReturn(expectedBooks);

    List<BookDto> actualBooks = libraryFeignClient.getBooksByIsbns(isbns);

    assertThat(actualBooks).isEqualTo(expectedBooks);
  }

  @Test
  public void testGetBooksByIsbnsNotFound() {
    List<String> isbns = List.of("978-0-399-15779-1", "978-0-399-15779-2");

    when(libraryFeignClientMock.getBooksByIsbns(isbns)).thenThrow(new RuntimeException("404 Not Found"));

    assertThatThrownBy(() -> libraryFeignClient.getBooksByIsbns(isbns))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("404 Not Found");
  }
}
