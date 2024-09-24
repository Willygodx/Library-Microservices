package by.ruslan.freebooksservice.exception.exceptions;

public class BookCannotBeDeletedException extends RuntimeException {
  public BookCannotBeDeletedException(Long id) {
    super(String.format("Book with id %d was taken. It cannot be deleted!", id));
  }
}
