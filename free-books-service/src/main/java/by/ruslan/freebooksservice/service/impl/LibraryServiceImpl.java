package by.ruslan.freebooksservice.service.impl;

import by.ruslan.freebooksservice.controller.outer.LibraryFeignClient;
import by.ruslan.freebooksservice.dto.LibraryDto;
import by.ruslan.freebooksservice.dto.BookDto;
import by.ruslan.freebooksservice.exception.exceptions.BookAlreadyReturnedException;
import by.ruslan.freebooksservice.exception.exceptions.BookAlreadyTakenException;
import by.ruslan.freebooksservice.exception.exceptions.BookCannotBeDeletedException;
import by.ruslan.freebooksservice.exception.exceptions.BookNotFoundByIsbnException;
import by.ruslan.freebooksservice.exception.exceptions.BookWithSameIsbnException;
import by.ruslan.freebooksservice.mapper.LibraryMapper;
import by.ruslan.freebooksservice.model.Library;
import by.ruslan.freebooksservice.repository.LibraryRepository;
import by.ruslan.freebooksservice.service.LibraryService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
  private final LibraryRepository libraryRepository;

  private final LibraryFeignClient libraryFeignClient;

  private final LibraryMapper libraryMapper;

  public LibraryDto addBook(String isbn) {
    if (libraryRepository.existsByIsbn(isbn)) {
      throw new BookWithSameIsbnException(isbn);
    } else {

      Library library = new Library();
      library.setIsbn(isbn);

      libraryRepository.save(library);
      return libraryMapper.toDto(library);
    }
  }

  public LibraryDto updateBook(String isbn) {
    Library library = libraryRepository.findByIsbn(isbn)
        .orElseThrow(() -> new BookNotFoundByIsbnException(isbn));

    if (library.getReturnBy() != null && library.getBorrowedAt() != null) {
      throw new BookAlreadyTakenException(isbn);
    } else {

      LocalDateTime borrowedAt = LocalDateTime.now();
      library.setBorrowedAt(borrowedAt);

      library.setReturnBy(borrowedAt.plusDays(14));
      libraryRepository.save(library);

      return libraryMapper.toDto(library);
    }
  }

  public List<BookDto> getFreeBooks() {
    List<String> freeBookIsbns = libraryRepository.findAvailableBooks();
    return libraryFeignClient.getBooksByIsbns(freeBookIsbns);
  }

  @Transactional
  public void deleteBook(String isbn) {
    Library library = libraryRepository.findByIsbn(isbn)
        .orElseThrow(() -> new BookNotFoundByIsbnException(isbn));

    if (library.getBorrowedAt() != null && library.getReturnBy() != null) {
      throw new BookCannotBeDeletedException(isbn);
    } else {
      libraryRepository.deleteByIsbn(isbn);
    }
  }

  public LibraryDto returnBook(String isbn) {
    Library library = libraryRepository.findByIsbn(isbn)
        .orElseThrow(() -> new BookNotFoundByIsbnException(isbn));

    if (library.getBorrowedAt() == null && library.getReturnBy() == null) {
      throw new BookAlreadyReturnedException(isbn);
    } else {

      library.setReturnBy(null);
      library.setBorrowedAt(null);

      libraryRepository.save(library);

      return libraryMapper.toDto(library);
    }
  }
}
