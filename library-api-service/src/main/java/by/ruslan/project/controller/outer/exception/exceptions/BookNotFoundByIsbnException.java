package by.ruslan.project.controller.outer.exception.exceptions;

public class BookNotFoundByIsbnException extends RuntimeException {
  public BookNotFoundByIsbnException(String isbn) {
    super(String.format("Book with ISBN %s not found!", isbn));
  }
}
