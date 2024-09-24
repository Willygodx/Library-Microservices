package by.ruslan.project.service;

import by.ruslan.project.dto.BookDto;
import java.util.List;

public interface BookService {
  List<BookDto> getAllBooks();

  BookDto getBookByIsbn(String isbn);

  void createBook(BookDto bookDto);

  void updateBook(String isbn, BookDto bookDto);

  void deleteBook(String isbn) throws Exception;

  List<BookDto> getBooksByIsbns(List<String> isbns);

}
