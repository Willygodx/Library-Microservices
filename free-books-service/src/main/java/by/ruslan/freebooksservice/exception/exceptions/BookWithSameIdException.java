package by.ruslan.freebooksservice.exception.exceptions;

public class BookWithSameIdException extends RuntimeException {
  public BookWithSameIdException(Long id) {
    super(String.format("Book with id %d already exists!", id));
  }
}
