package by.ruslan.project.controller.inner.impl;

import by.ruslan.project.controller.inner.BookController;
import by.ruslan.project.dto.BookDto;
import by.ruslan.project.service.BookService;
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

  @GetMapping("/books/{id}")
  public BookDto getBookById(@PathVariable Long id) {
    return bookService.getBookById(id);
  }

  @GetMapping("/books/isbn/{isbn}")
  public BookDto getBookByIsbn(@PathVariable String isbn) {
    return bookService.getBookByIsbn(isbn);
  }

  @GetMapping("/books")
  public List<BookDto> getAllBooks() {
    return bookService.getAllBooks();
  }

  @PostMapping("/books")
  public BookDto createBook(@RequestBody BookDto bookDto) {
    bookService.createBook(bookDto);
    return bookDto;
  }

  @PostMapping("/books/by-ids")
  public List<BookDto> getBooksByIds(@RequestBody List<Long> ids) {
    return bookService.getBooksByIds(ids);
  }

  @PutMapping("/books/{id}")
  public BookDto updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
    bookService.updateBook(id, bookDto);
    return bookDto;
  }

  @DeleteMapping("/books")
  public void deleteBook(@RequestParam("id") Long id) {
    bookService.deleteBook(id);
  }
}
