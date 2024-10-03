package by.ruslan.project.controller.inner;

import by.ruslan.project.dto.BookDto;
import by.ruslan.project.dto.Marker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.validation.annotation.Validated;

@Tag(name = "Book Controller", description =
    "This controller provides basic CRUD operations with books from library")
@Validated
public interface BookController {

  @Operation(summary = "Retrieving a book by ISBN from DB")
  @SecurityRequirement(name = "JWT (all roles)")
  BookDto getBookByIsbn(String isbn);

  @Operation(summary = "Retrieving a list of all books from DB")
  @SecurityRequirement(name = "JWT (all roles)")
  List<BookDto> getAllBooks();

  @Operation(summary = "Creates a new book in DB")
  @SecurityRequirement(name = "JWT (librarian role)")
  @Validated(Marker.OnCreate.class)
  BookDto createBook(@Valid BookDto bookDto);

  @Operation(summary = "Retrieving a list of books by a list of isbns")
  @SecurityRequirement(name = "JWT (all roles)")
  List<BookDto> getBooksByIsbns(List<String> isbns);

  @Operation(summary = "Updates existing book by isbn in DB")
  @SecurityRequirement(name = "JWT (librarian role)")
  @Validated(Marker.OnUpdate.class)
  BookDto updateBook(String isbn, @Valid BookDto bookDto);

  @Operation(summary = "Deletes book by isbn")
  @SecurityRequirement(name = "JWT (librarian role)")
  void deleteBook(String isbn) throws Exception;
}
