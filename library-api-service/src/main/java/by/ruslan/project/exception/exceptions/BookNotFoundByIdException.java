package by.ruslan.project.exception.exceptions;

public class BookNotFoundByIdException extends RuntimeException {
  public BookNotFoundByIdException(Long bookId) {
    super(String.format("Book with ID %d not found!", bookId));
  }
}
