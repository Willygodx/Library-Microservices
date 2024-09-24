package by.ruslan.freebooksservice.exception.exceptions;

public class BookAlreadyReturnedException extends RuntimeException {
  public BookAlreadyReturnedException(String isbn) {
    super(String.format("Book with ISBN %s was already returned!", isbn));
  }
}
