package by.ruslan.freebooksservice.controller.inner.impl;

import by.ruslan.freebooksservice.controller.inner.LibraryController;
import by.ruslan.freebooksservice.dto.LibraryDto;
import by.ruslan.freebooksservice.dto.BookDto;
import by.ruslan.freebooksservice.service.impl.LibraryServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/free-books")
@RequiredArgsConstructor
public class LibraryControllerImpl implements LibraryController {
  private final LibraryServiceImpl libraryServiceImpl;

  @PostMapping()
  public LibraryDto addBook(@RequestParam("bookId") Long bookId) {
    return libraryServiceImpl.addBook(bookId);
  }

  @PutMapping()
  public LibraryDto takeBook(@RequestParam("bookId") Long bookId) {
    return libraryServiceImpl.updateBook(bookId);
  }

  @GetMapping()
  public List<BookDto> getFreeBooks() {
    return libraryServiceImpl.getFreeBooks();
  }

}
