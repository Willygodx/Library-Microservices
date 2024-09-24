package by.ruslan.freebooksservice.exception.exceptions;

public class BookCannotBeDeletedException extends RuntimeException {
  public BookCannotBeDeletedException(String isbn) {
    super(String.format("Book with ISBN %s was taken. It cannot be deleted!", isbn));
  }
}
