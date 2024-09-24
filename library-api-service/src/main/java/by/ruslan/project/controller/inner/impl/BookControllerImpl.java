package by.ruslan.project.controller.inner.impl;

import by.ruslan.project.controller.inner.BookController;
import by.ruslan.project.dto.BookDto;
import by.ruslan.project.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
public class BookControllerImpl implements BookController {

  private final BookService bookService;

  @GetMapping("/books/get/{isbn}")
  public BookDto getBookByIsbn(@PathVariable("isbn") String isbn) {
    return bookService.getBookByIsbn(isbn);
  }

  @GetMapping("/books")
  public List<BookDto> getAllBooks() {
    return bookService.getAllBooks();
  }

  @PostMapping("/books")
  public BookDto createBook(@RequestBody @Valid BookDto bookDto) {
    bookService.createBook(bookDto);
    return bookDto;
  }

  @GetMapping("/books/{isbns}")
  public List<BookDto> getBooksByIsbns(@PathVariable List<String> isbns) {
    return bookService.getBooksByIsbns(isbns);
  }

  @PutMapping("/books/{isbn}")
  public BookDto updateBook(@PathVariable("isbn") String isbn, @RequestBody @Valid BookDto bookDto) {
    bookService.updateBook(isbn, bookDto);
    return bookDto;
  }

  @DeleteMapping("/books")
  public void deleteBook(@RequestParam("isbn") String isbn) throws Exception {
    bookService.deleteBook(isbn);
  }
}
