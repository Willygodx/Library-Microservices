package by.ruslan.freebooksservice.exception.exceptions;

public class BookAlreadyTakenException extends RuntimeException {
  public BookAlreadyTakenException(Long id) {
    super(String.format("Book with id %d already taken!", id));
  }
}
