package by.ruslan.project.service.impl;

import by.ruslan.project.controller.outer.LibraryFeignClient;
import by.ruslan.project.dto.BookDto;
import by.ruslan.project.controller.outer.exception.exceptions.BookNotFoundByIsbnException;
import by.ruslan.project.controller.outer.exception.exceptions.BookWithSameIsbnException;
import by.ruslan.project.mapper.BookMapper;
import by.ruslan.project.model.Book;
import by.ruslan.project.repositories.BookRepository;
import by.ruslan.project.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  private final BookMapper bookMapper;

  private final LibraryFeignClient libraryFeignClient;

  @Override
  public List<BookDto> getAllBooks() {
    return bookMapper.toListDto(bookRepository.findAll());
  }

  @Override
  public BookDto getBookByIsbn(String isbn) {
    return bookMapper.toDto(bookRepository.findBookByIsbn(isbn)
        .orElseThrow(() -> new BookNotFoundByIsbnException(isbn)));
  }

  @Override
  @Transactional
  public void createBook(BookDto bookDto) {
    if (bookRepository.existsByIsbn(bookDto.getIsbn())) {
      throw new BookWithSameIsbnException(bookDto.getIsbn());
    } else {
      Book newBook = bookRepository.save(bookMapper.toEntity(bookDto));
      libraryFeignClient.addBook(newBook.getIsbn());
    }
  }

  @Override
  public void updateBook(String isbn, BookDto bookDto) {
    Book existingBook = bookRepository.findBookByIsbn(isbn)
        .orElseThrow(() -> new BookNotFoundByIsbnException(isbn));

    bookMapper.updateBookFromDto(bookDto, existingBook);

    bookRepository.save(existingBook);
  }

  @Override
  @Transactional
  public void deleteBook(String isbn) {
    if (!bookRepository.existsByIsbn(isbn)) {
      throw new BookNotFoundByIsbnException(isbn);
    } else {
      libraryFeignClient.deleteBook(isbn);
      bookRepository.deleteByIsbn(isbn);
    }
  }

  @Override
  public List<BookDto> getBooksByIsbns(List<String> isbns) {
    return bookMapper.toListDto(bookRepository.findBookByIsbnIn(isbns));
  }
}
