package by.ruslan.freebooksservice.controller.inner.impl;

import by.ruslan.freebooksservice.controller.inner.LibraryController;
import by.ruslan.freebooksservice.dto.LibraryDto;
import by.ruslan.freebooksservice.dto.BookDto;
import by.ruslan.freebooksservice.service.impl.LibraryServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
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
  public LibraryDto addBook(@RequestParam("isbn") String isbn) {
    return libraryServiceImpl.addBook(isbn);
  }

  @PutMapping("/take")
  public LibraryDto takeBook(@RequestParam("isbn") String isbn) {
    return libraryServiceImpl.updateBook(isbn);
  }

  @GetMapping()
  public List<BookDto> getFreeBooks() {
    return libraryServiceImpl.getFreeBooks();
  }

  @DeleteMapping()
  public void deleteBook(@RequestParam("isbn") String isbn) {
    libraryServiceImpl.deleteBook(isbn);
  }

  @PutMapping("/return")
  public LibraryDto returnBook(@RequestParam("isbn") String isbn) {
    return libraryServiceImpl.returnBook(isbn);
  }

}
