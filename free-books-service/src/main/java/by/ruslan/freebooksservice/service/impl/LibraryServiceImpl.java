package by.ruslan.freebooksservice.service.impl;

import by.ruslan.freebooksservice.controller.outer.LibraryFeignClient;
import by.ruslan.freebooksservice.dto.LibraryDto;
import by.ruslan.freebooksservice.dto.BookDto;
import by.ruslan.freebooksservice.mapper.LibraryMapper;
import by.ruslan.freebooksservice.model.Library;
import by.ruslan.freebooksservice.repository.LibraryRepository;
import by.ruslan.freebooksservice.service.LibraryService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
  private final LibraryRepository libraryRepository;

  private final LibraryFeignClient libraryFeignClient;

  private final LibraryMapper libraryMapper;

  public LibraryDto addBook(Long bookId) {
    Library library = new Library();
    library.setBookId(bookId);

    libraryRepository.save(library);
    return libraryMapper.toDto(library);
  }

  public LibraryDto updateBook(Long bookId) {
    Optional<Library> bookOpt = libraryRepository.findByBookId(bookId);
    if (bookOpt.isPresent()) {
      Library library = bookOpt.get();

      LocalDateTime borrowedAt = LocalDateTime.now();
      library.setBorrowedAt(borrowedAt);

      library.setReturnBy(borrowedAt.plusDays(14));
      libraryRepository.save(library);

      return libraryMapper.toDto(library);
    }
    return null;
  }

  public List<BookDto> getFreeBooks() {
    List<Long> freeBookIds = libraryRepository.findAvailableBooks();
    return libraryFeignClient.getBooksByIds(freeBookIds);
  }
}
