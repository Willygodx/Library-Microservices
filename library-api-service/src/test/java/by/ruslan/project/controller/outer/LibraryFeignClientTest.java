package by.ruslan.project.controller.outer;

import by.ruslan.project.configuration.FeignClientConfig;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class LibraryFeignClientTest {

  @Mock
  private LibraryFeignClient libraryFeignClient;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testAddBook() {
    String isbn = "178-0-06-093546-1";
    libraryFeignClient.addBook(isbn);
    verify(libraryFeignClient, times(1)).addBook(isbn);
  }

  @Test
  public void testDeleteBook() {
    String isbn = "178-0-06-093546-1";
    libraryFeignClient.deleteBook(isbn);
    verify(libraryFeignClient, times(1)).deleteBook(isbn);
  }

  @Test
  public void testAddBookErrorHandling() {
    String isbn = "178-0-06-093546-1";
    Request request = Request.create(Request.HttpMethod.POST, "/free-books", Collections.emptyMap(), null, StandardCharsets.UTF_8, new RequestTemplate());
    doThrow(new FeignException.FeignClientException(404, "Not Found", request, null, null)).when(libraryFeignClient).addBook(isbn);

    try {
      libraryFeignClient.addBook(isbn);
    } catch (FeignException e) {
      assert e.status() == 404;
    }
  }

  @Test
  public void testDeleteBookErrorHandling() {
    String isbn = "178-0-06-093546-1";
    Request request = Request.create(Request.HttpMethod.DELETE, "/free-books", Collections.emptyMap(), null, StandardCharsets.UTF_8, new RequestTemplate());
    doThrow(new FeignException.FeignClientException(404, "Not Found", request, null, null)).when(libraryFeignClient).deleteBook(isbn);

    try {
      libraryFeignClient.deleteBook(isbn);
    } catch (FeignException e) {
      assert e.status() == 404;
    }
  }
}
