package by.ruslan.freebooksservice.exception.exceptions;

public class BookNotFoundByIdException extends RuntimeException {
  public BookNotFoundByIdException(Long id) {
    super(String.format("Book with id %d not found!", id));
  }
}
