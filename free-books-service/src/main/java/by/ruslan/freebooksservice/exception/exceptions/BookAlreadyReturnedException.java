package by.ruslan.freebooksservice.exception.exceptions;

public class BookAlreadyReturnedException extends RuntimeException {
  public BookAlreadyReturnedException(Long id) {
    super(String.format("Book with id %d was already returned!", id));
  }
}
