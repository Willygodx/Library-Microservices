package by.ruslan.project.controller.inner;

import by.ruslan.project.dto.BookDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Book Controller", description =
    "This controller provides basic CRUD operations with books from library")
public interface BookController {

  @Operation(summary = "Retrieving book by id from DB")
  BookDto getBookById(Long id);

  @Operation(summary = "Retrieving a book by ISBN from DB")
  BookDto getBookByIsbn(String isbn);

  @Operation(summary = "Retrieving a list of all books from DB")
  List<BookDto> getAllBooks();

  @Operation(summary = "Creates a new book in DB")
  BookDto createBook(BookDto bookDto);

  @Operation(summary = "Retrieving a list of books by a list of ids")
  List<BookDto> getBooksByIds(List<Long> ids);

  @Operation(summary = "Updates existing book by id in DB")
  BookDto updateBook(Long id, BookDto bookDto);

  @Operation(summary = "Deletes book by id")
  void deleteBook(Long id);
}
