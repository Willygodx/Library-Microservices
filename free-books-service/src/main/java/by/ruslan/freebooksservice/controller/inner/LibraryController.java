package by.ruslan.freebooksservice.controller.inner;

import by.ruslan.freebooksservice.dto.LibraryDto;
import by.ruslan.freebooksservice.dto.BookDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Book Library Controller", description =
    "This controller provides some actions with books in library")
public interface LibraryController {

  @Operation(summary = "Add a book into a library with information about " +
      "borrow and return date")
  LibraryDto addBook(Long bookId);

  @Operation(summary = "Taking a book from a library (columns from database will be fulfilled)")
  LibraryDto takeBook(Long bookId);

  @Operation(summary = "Retrieving a list with a free books")
  List<BookDto> getFreeBooks();

}
