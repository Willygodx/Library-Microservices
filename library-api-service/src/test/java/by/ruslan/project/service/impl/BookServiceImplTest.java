package by.ruslan.project.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import by.ruslan.project.controller.outer.LibraryFeignClient;
import by.ruslan.project.dto.BookDto;
import by.ruslan.project.controller.outer.exception.exceptions.BookWithSameIsbnException;
import by.ruslan.project.controller.outer.exception.exceptions.BookNotFoundByIsbnException;
import by.ruslan.project.model.Book;
import by.ruslan.project.repositories.BookRepository;
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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ActiveProfiles("Book service tests")
public class BookServiceImplTest {

  @Container
  private static PostgreSQLContainer<?> postgresDB =
      new PostgreSQLContainer<>("postgres:latest").withDatabaseName("testdb")
          .withUsername("testuser").withPassword("testpass");

  @Autowired
  private BookRepository bookRepository;

  @MockBean
  private LibraryFeignClient libraryFeignClient;

  @Autowired
  private BookServiceImpl bookService;

  @DynamicPropertySource
  static void postgresProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresDB::getJdbcUrl);
    registry.add("spring.datasource.username", postgresDB::getUsername);
    registry.add("spring.datasource.password", postgresDB::getPassword);
  }

  @BeforeEach
  void setup() {
    bookRepository.deleteAll();
  }

  @Test
  void testCreateBookSuccess() {
    BookDto bookDto =
        new BookDto("978-0-06-093546-7", "Test Book", "Genre", "Description", "Author");

    Mockito.doNothing().when(libraryFeignClient).addBook(Mockito.anyString());

    bookService.createBook(bookDto);

    List<Book> books = bookRepository.findAll();
    assertEquals(1, books.size());
    assertEquals("978-0-06-093546-7", books.get(0).getIsbn());
  }

  @Test
  void testCreateBookWithDuplicateIsbn() {
    Book existingBook =
        new Book("978-0-06-093546-7", "Test Book", "Genre", "Description", "Author");
    bookRepository.save(existingBook);

    BookDto bookDto =
        new BookDto("978-0-06-093546-7", "New Book", "New Genre", "New Description", "New Author");

    assertThrows(BookWithSameIsbnException.class, () -> bookService.createBook(bookDto));
  }

  @Test
  void testDeleteBookSuccess() {
    Book book = new Book("978-0-06-093546-7", "Test Book", "Genre", "Description", "Author");
    bookRepository.save(book);

    Mockito.doNothing().when(libraryFeignClient).deleteBook(Mockito.anyString());

    bookService.deleteBook("978-0-06-093546-7");

    assertTrue(bookRepository.findAll().isEmpty());
  }

  @Test
  void testDeleteBookNotFound() {
    assertThrows(BookNotFoundByIsbnException.class, () -> bookService.deleteBook("Not exist"));
  }

  @Test
  void testUpdateBookSuccess() {
    Book book = new Book("978-0-06-093546-7", "Test Book", "Genre", "Description", "Author");
    bookRepository.save(book);

    BookDto bookDto =
        new BookDto("978-0-06-001541-2", "New Book", "New Genre", "New Description", "New Author");
    bookService.updateBook("978-0-06-093546-7", bookDto);

    Book updatedBook = bookRepository.findBookByIsbn("978-0-06-001541-2").orElseThrow();
    assertEquals("New Book", updatedBook.getName());
  }

  @Test
  void testUpdateBookNotFound() {
    BookDto bookDto =
        new BookDto("978-0-06-001541-2", "New Book", "New Genre", "New Description", "New Author");

    assertThrows(BookNotFoundByIsbnException.class,
        () -> bookService.updateBook("978-0-06-001541-2", bookDto));
  }

  @Test
  void testGetAllBooks() {
    Book book1 =
        new Book("178-0-06-093546-1", "Test Book 1", "Genre 1", "Description 1", "Author 1");
    Book book2 =
        new Book("278-0-06-093546-2", "Test Book 2", "Genre 2", "Description 2", "Author 2");
    bookRepository.saveAll(List.of(book1, book2));

    List<BookDto> books = bookService.getAllBooks();
    assertEquals(2, books.size());
  }

  @Test
  void testGetBookByIsbnNotFound() {
    assertThrows(BookNotFoundByIsbnException.class, () -> bookService.getBookByIsbn("Not Exist"));
  }

  @Test
  void testCreateBookFeignClientThrowsException() {
    BookDto bookDto =
        new BookDto("978-0-06-001541-2", "New Book", "New Genre", "New Description", "New Author");

    Mockito.doThrow(new RuntimeException("Feign client error")).when(libraryFeignClient)
        .addBook(Mockito.anyString());

    assertThrows(RuntimeException.class, () -> bookService.createBook(bookDto));

    assertTrue(bookRepository.findAll().isEmpty());
  }

  @Test
  void testDeleteBookFeignClientThrowsException() {
    Book book = new Book("978-0-06-093546-7", "Test Book", "Genre", "Description", "Author");
    bookRepository.save(book);

    Mockito.doThrow(new RuntimeException("Feign client error")).when(libraryFeignClient)
        .deleteBook(Mockito.anyString());

    assertThrows(RuntimeException.class, () -> bookService.deleteBook("978-0-06-093546-7"));

    assertTrue(bookRepository.existsByIsbn("978-0-06-093546-7"));
  }

  @Test
  void testUpdateBookWithoutChanges() {
    Book book = new Book("978-0-06-093546-7", "Test Book", "Genre", "Description", "Author");
    bookRepository.save(book);

    BookDto bookDto = new BookDto("978-0-06-093546-7", "Test Book", "Genre", "Description", "Author");
    bookService.updateBook("978-0-06-093546-7", bookDto);

    Book updatedBook = bookRepository.findBookByIsbn("978-0-06-093546-7").orElseThrow();
    assertEquals("Test Book", updatedBook.getName());
  }

  @Test
  void testGetBooksByEmptyIsbns() {
    List<BookDto> books = bookService.getBooksByIsbns(List.of());
    assertTrue(books.isEmpty());
  }

  @Test
  void testGetBooksByNonExistentIsbns() {
    List<String> isbns = List.of("000-0-00-000000-0", "000-0-00-000000-1");
    List<BookDto> books = bookService.getBooksByIsbns(isbns);
    assertTrue(books.isEmpty());
  }

  @Test
  void testUpdateBookWithSameIsbn() {
    Book book = new Book("978-0-06-093546-7", "Test Book", "Genre", "Description", "Author");
    bookRepository.save(book);

    BookDto bookDto = new BookDto("978-0-06-093546-7", "Updated Book", "Updated Genre", "Updated Description", "Updated Author");
    bookService.updateBook("978-0-06-093546-7", bookDto);

    Book updatedBook = bookRepository.findBookByIsbn("978-0-06-093546-7").orElseThrow();
    assertEquals("Updated Book", updatedBook.getName());
  }

}

