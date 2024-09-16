package by.ruslan.freebooksservice.service;

import by.ruslan.freebooksservice.dto.BookDto;
import by.ruslan.freebooksservice.dto.LibraryDto;
import java.util.List;

public interface LibraryService {
  LibraryDto addBook(Long bookId);

  LibraryDto updateBook(Long bookId);

  List<BookDto> getFreeBooks();
}
