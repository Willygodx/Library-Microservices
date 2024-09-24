package by.ruslan.freebooksservice.service.impl;

import by.ruslan.freebooksservice.controller.outer.LibraryFeignClient;
import by.ruslan.freebooksservice.dto.LibraryDto;
import by.ruslan.freebooksservice.dto.BookDto;
import by.ruslan.freebooksservice.exception.exceptions.BookAlreadyReturnedException;
import by.ruslan.freebooksservice.exception.exceptions.BookAlreadyTakenException;
import by.ruslan.freebooksservice.exception.exceptions.BookCannotBeDeletedException;
import by.ruslan.freebooksservice.exception.exceptions.BookNotFoundByIdException;
import by.ruslan.freebooksservice.exception.exceptions.BookWithSameIdException;
import by.ruslan.freebooksservice.mapper.LibraryMapper;
import by.ruslan.freebooksservice.model.Library;
import by.ruslan.freebooksservice.repository.LibraryRepository;
import by.ruslan.freebooksservice.service.LibraryService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
  private final LibraryRepository libraryRepository;

  private final LibraryFeignClient libraryFeignClient;

  private final LibraryMapper libraryMapper;

  public LibraryDto addBook(Long bookId) {
    if (libraryRepository.existsById(bookId)) {
      throw new BookWithSameIdException(bookId);
    } else {

      Library library = new Library();
      library.setBookId(bookId);

      libraryRepository.save(library);
      return libraryMapper.toDto(library);
    }
  }

  public LibraryDto updateBook(Long bookId) {
    Library library = libraryRepository.findByBookId(bookId)
        .orElseThrow(() -> new BookNotFoundByIdException(bookId));

    if (library.getReturnBy() != null && library.getBorrowedAt() != null) {
      throw new BookAlreadyTakenException(bookId);
    } else {

      LocalDateTime borrowedAt = LocalDateTime.now();
      library.setBorrowedAt(borrowedAt);

      library.setReturnBy(borrowedAt.plusDays(14));
      libraryRepository.save(library);

      return libraryMapper.toDto(library);
    }
  }

  public List<BookDto> getFreeBooks() {
    List<Long> freeBookIds = libraryRepository.findAvailableBooks();
    return libraryFeignClient.getBooksByIds(freeBookIds);
  }

  public void deleteBook(Long bookId) throws Exception {
    Library library = libraryRepository.findByBookId(bookId)
        .orElseThrow(() -> new BookNotFoundByIdException(bookId));

    if (library.getBorrowedAt() != null && library.getReturnBy() != null) {
      throw new BookCannotBeDeletedException(bookId);
    } else {
      libraryRepository.deleteById(bookId);
    }
  }

  public LibraryDto returnBook(Long bookId) {
    Library library = libraryRepository.findByBookId(bookId)
        .orElseThrow(() -> new BookNotFoundByIdException(bookId));

    if (library.getBorrowedAt() == null && library.getReturnBy() == null) {
      throw new BookAlreadyReturnedException(bookId);
    } else {

      library.setReturnBy(null);
      library.setBorrowedAt(null);

      libraryRepository.save(library);

      return libraryMapper.toDto(library);
    }
  }
}
