package by.ruslan.freebooksservice.service;

import by.ruslan.freebooksservice.dto.BookDto;
import by.ruslan.freebooksservice.dto.LibraryDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface LibraryService {
  LibraryDto addBook(String isbn);

  LibraryDto updateBook(String isbn);

  List<BookDto> getFreeBooks();

  void deleteBook(String isbn) throws Exception;

  LibraryDto returnBook(String isbn);

}
