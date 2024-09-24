package by.ruslan.freebooksservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import by.ruslan.freebooksservice.controller.outer.LibraryFeignClient;
import by.ruslan.freebooksservice.dto.BookDto;
import by.ruslan.freebooksservice.dto.LibraryDto;
import by.ruslan.freebooksservice.exception.exceptions.BookAlreadyReturnedException;
import by.ruslan.freebooksservice.exception.exceptions.BookAlreadyTakenException;
import by.ruslan.freebooksservice.exception.exceptions.BookCannotBeDeletedException;
import by.ruslan.freebooksservice.exception.exceptions.BookNotFoundByIsbnException;
import by.ruslan.freebooksservice.exception.exceptions.BookWithSameIsbnException;
import by.ruslan.freebooksservice.mapper.LibraryMapper;
import by.ruslan.freebooksservice.model.Library;
import by.ruslan.freebooksservice.repository.LibraryRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ActiveProfiles("Library service test")
public class LibraryServiceImplTest {

  @Container
  private static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:latest")
      .withDatabaseName("testdb")
      .withUsername("testuser")
      .withPassword("testpass");

  @Autowired
  private LibraryRepository libraryRepository;

  @Autowired
  private LibraryMapper libraryMapper;

  @MockBean
  private LibraryFeignClient libraryFeignClient;

  @Autowired
  private LibraryServiceImpl libraryService;

  @DynamicPropertySource
  static void postgresProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresDB::getJdbcUrl);
    registry.add("spring.datasource.username", postgresDB::getUsername);
    registry.add("spring.datasource.password", postgresDB::getPassword);
  }

  @BeforeEach
  void setup() {
    libraryRepository.deleteAll();
  }

  @Test
  void testAddBookSuccess() {
    String isbn = "978-1-306-10299-7";

    LibraryDto libraryDto = libraryService.addBook(isbn);

    assertEquals(isbn, libraryDto.getIsbn());
    assertTrue(libraryRepository.existsByIsbn(isbn));
  }

  @Test
  void testAddBookWithDuplicateIsbn() {
    Library library = new Library();
    library.setIsbn("978-1-306-10299-7");
    libraryRepository.save(library);

    assertThrows(BookWithSameIsbnException.class, () -> libraryService.addBook("978-1-306-10299-7"));
  }

  @Test
  void testUpdateBookSuccess() {
    Library library = new Library();
    library.setIsbn("978-1-306-10299-7");
    libraryRepository.save(library);

    LibraryDto updatedDto = libraryService.updateBook("978-1-306-10299-7");

    assertNotNull(updatedDto.getBorrowedAt());
    assertNotNull(updatedDto.getReturnBy());
  }

  @Test
  void testUpdateBookAlreadyTaken() {
    Library library = new Library();
    library.setIsbn("978-1-306-10299-7");
    library.setBorrowedAt(LocalDateTime.now());
    library.setReturnBy(LocalDateTime.now().plusDays(14));
    libraryRepository.save(library);

    assertThrows(BookAlreadyTakenException.class, () -> libraryService.updateBook("978-1-306-10299-7"));
  }

  @Test
  void testGetFreeBooksSuccess() {
    Library library = new Library();
    library.setIsbn("978-1-306-10299-7");
    libraryRepository.save(library);

    List<String> freeBooksIsbns = List.of("978-1-306-10299-7");
    Mockito.when(libraryFeignClient.getBooksByIsbns(freeBooksIsbns)).thenReturn(List.of(new BookDto("978-1-306-10299-7", "Test Book", "Test Genre", "Test Description", "Test Author")));

    List<BookDto> freeBooks = libraryService.getFreeBooks();

    assertEquals(1, freeBooks.size());
    assertEquals("978-1-306-10299-7", freeBooks.get(0).getIsbn());
  }

  @Test
  void testDeleteBookSuccess() {
    Library library = new Library();
    library.setIsbn("978-1-306-10299-7");
    libraryRepository.save(library);

    libraryService.deleteBook("978-1-306-10299-7");

    assertFalse(libraryRepository.existsByIsbn("978-1-306-10299-7"));
  }

  @Test
  void testDeleteBookAlreadyTaken() {
    Library library = new Library();
    library.setIsbn("978-1-306-10299-7");
    library.setBorrowedAt(LocalDateTime.now());
    library.setReturnBy(LocalDateTime.now().plusDays(14));
    libraryRepository.save(library);

    assertThrows(BookCannotBeDeletedException.class, () -> libraryService.deleteBook("978-1-306-10299-7"));
  }

  @Test
  void testReturnBookSuccess() {
    Library library = new Library();
    library.setIsbn("978-1-306-10299-7");
    library.setBorrowedAt(LocalDateTime.now());
    library.setReturnBy(LocalDateTime.now().plusDays(14));
    libraryRepository.save(library);

    LibraryDto libraryDto = libraryService.returnBook("978-1-306-10299-7");

    assertNull(libraryDto.getBorrowedAt());
    assertNull(libraryDto.getReturnBy());
  }

  @Test
  void testReturnBookAlreadyReturned() {
    Library library = new Library();
    library.setIsbn("978-1-306-10299-7");
    libraryRepository.save(library);

    assertThrows(BookAlreadyReturnedException.class, () -> libraryService.returnBook("978-1-306-10299-7"));
  }

  @Test
  void testDeleteBookNotFound() {
    assertThrows(BookNotFoundByIsbnException.class, () -> libraryService.deleteBook("Not exist"));
  }

  @Test
  void testGetFreeBooksWhenNoneAvailable() {
    Mockito.when(libraryFeignClient.getBooksByIsbns(Collections.emptyList())).thenReturn(Collections.emptyList());

    List<BookDto> freeBooks = libraryService.getFreeBooks();

    assertTrue(freeBooks.isEmpty());
  }

  @Test
  void testReturnNonExistentBook() {
    assertThrows(BookNotFoundByIsbnException.class, () -> libraryService.returnBook("Not exist"));
  }

  @Test
  void testDeleteNonExistentBook() {
    assertThrows(BookNotFoundByIsbnException.class, () -> libraryService.deleteBook("Not exist"));
  }

  @Test
  void testReturnBookClearsDates() {
    Library library = new Library();
    library.setIsbn("978-1-306-10299-7");
    library.setBorrowedAt(LocalDateTime.now());
    library.setReturnBy(LocalDateTime.now().plusDays(14));
    libraryRepository.save(library);

    LibraryDto libraryDto = libraryService.returnBook("978-1-306-10299-7");

    assertNull(libraryDto.getBorrowedAt());
    assertNull(libraryDto.getReturnBy());
  }

  @Test
  @Transactional
  void testDeleteBookTransactionRollbackOnFailure() {
    Library library = new Library();
    library.setIsbn("978-1-306-10299-7");
    library.setBorrowedAt(LocalDateTime.now());
    library.setReturnBy(LocalDateTime.now().plusDays(14));
    libraryRepository.save(library);

    assertThrows(BookCannotBeDeletedException.class, () -> libraryService.deleteBook("978-1-306-10299-7"));

    assertTrue(libraryRepository.existsByIsbn("978-1-306-10299-7"));
  }
}
