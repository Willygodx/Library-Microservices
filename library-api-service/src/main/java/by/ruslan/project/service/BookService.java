package by.ruslan.project.service;

import by.ruslan.project.dto.BookDto;
import java.util.List;

public interface BookService {
  List<BookDto> getAllBooks();

  BookDto getBookById(Long id);

  BookDto getBookByIsbn(String isbn);

  void createBook(BookDto bookDto);

  void updateBook(Long id, BookDto bookDto);

  void deleteBook(Long id);

  List<BookDto> getBooksByIds(List<Long> ids);

}
