package by.ruslan.freebooksservice.controller.inner;

import by.ruslan.freebooksservice.dto.LibraryDto;
import by.ruslan.freebooksservice.dto.BookDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book Library Controller", description =
    "This controller provides some actions with books in library")
@RestController
public interface LibraryController {

  @Operation(summary = "Add a book into a library with information about " +
      "borrow and return date")
  @SecurityRequirement(name = "JWT (librarian role)")
  LibraryDto addBook(String isbn);

  @Operation(summary = "Taking a book from a library (columns from database will be fulfilled)")
  @SecurityRequirement(name = "JWT (all roles)")
  LibraryDto takeBook(String isbn);

  @Operation(summary = "Retrieving a list with a free books")
  @SecurityRequirement(name = "JWT (all roles)")
  List<BookDto> getFreeBooks();

  @Operation(summary = "Deletes book from database")
  @SecurityRequirement(name = "JWT (librarian role)")
  void deleteBook(String isbn) throws Exception;

  @Operation(summary = "Returning a book to a library (columns from database will be null except id)")
  @SecurityRequirement(name = "JWT (all roles)")
  LibraryDto returnBook(String isbn);
}
