package by.ruslan.freebooksservice.exception.exceptions;

public class BookAlreadyTakenException extends RuntimeException {
  public BookAlreadyTakenException(String isbn) {
    super(String.format("Book with ISBN %s already taken!", isbn));
  }
}
