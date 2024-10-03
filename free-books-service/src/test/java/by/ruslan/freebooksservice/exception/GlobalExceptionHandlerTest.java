package by.ruslan.freebooksservice.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.ruslan.freebooksservice.dto.ExceptionDto;
import by.ruslan.freebooksservice.exception.exceptions.BookAlreadyReturnedException;
import by.ruslan.freebooksservice.exception.exceptions.BookAlreadyTakenException;
import by.ruslan.freebooksservice.exception.exceptions.BookCannotBeDeletedException;
import by.ruslan.freebooksservice.exception.exceptions.BookNotFoundByIsbnException;
import by.ruslan.freebooksservice.exception.exceptions.BookWithSameIsbnException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GlobalExceptionHandler.class})
@ExtendWith(SpringExtension.class)
class GlobalExceptionHandlerTest {

  @Autowired
  private GlobalExceptionHandler globalExceptionHandler;

  @Test
  void testHandleBookNotFoundByIsbnException() {
    ExceptionDto actualHandleBookNotFoundByIsbnExceptionResult = globalExceptionHandler
        .handleBookNotFoundByIsbnException(new BookNotFoundByIsbnException("Isbn"));

    assertEquals("Book with ISBN Isbn not found!",
        actualHandleBookNotFoundByIsbnExceptionResult.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, actualHandleBookNotFoundByIsbnExceptionResult.getStatus());
  }

  @Test
  void testHandleBookNotFoundByIsbnException2() {
    BookNotFoundByIsbnException exception = mock(BookNotFoundByIsbnException.class);
    when(exception.getMessage()).thenReturn("Not all who wander are lost");

    ExceptionDto actualHandleBookNotFoundByIsbnExceptionResult = globalExceptionHandler
        .handleBookNotFoundByIsbnException(exception);

    verify(exception).getMessage();
    assertEquals("Not all who wander are lost",
        actualHandleBookNotFoundByIsbnExceptionResult.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, actualHandleBookNotFoundByIsbnExceptionResult.getStatus());
  }

  @Test
  void testHandleBookWithSameIsbnException() {
    ExceptionDto actualHandleBookWithSameIsbnExceptionResult = globalExceptionHandler
        .handleBookWithSameIsbnException(new BookWithSameIsbnException("Isbn"));

    assertEquals("Book with ISBN Isbn already exists!",
        actualHandleBookWithSameIsbnExceptionResult.getMessage());
    assertEquals(HttpStatus.CONFLICT, actualHandleBookWithSameIsbnExceptionResult.getStatus());
  }

  @Test
  void testHandleBookWithSameIsbnException2() {
    BookWithSameIsbnException exception = mock(BookWithSameIsbnException.class);
    when(exception.getMessage()).thenReturn("Not all who wander are lost");

    ExceptionDto actualHandleBookWithSameIsbnExceptionResult = globalExceptionHandler
        .handleBookWithSameIsbnException(exception);

    verify(exception).getMessage();
    assertEquals("Not all who wander are lost",
        actualHandleBookWithSameIsbnExceptionResult.getMessage());
    assertEquals(HttpStatus.CONFLICT, actualHandleBookWithSameIsbnExceptionResult.getStatus());
  }

  @Test
  void testHandleBookAlreadyReturnedException() {
    ExceptionDto actualHandleBookAlreadyReturnedExceptionResult = globalExceptionHandler
        .handleBookAlreadyReturnedException(new BookAlreadyReturnedException("Isbn"));

    assertEquals("Book with ISBN Isbn was already returned!",
        actualHandleBookAlreadyReturnedExceptionResult.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST,
        actualHandleBookAlreadyReturnedExceptionResult.getStatus());
  }

  @Test
  void testHandleBookAlreadyReturnedException2() {
    BookAlreadyReturnedException exception = mock(BookAlreadyReturnedException.class);
    when(exception.getMessage()).thenReturn("Not all who wander are lost");

    ExceptionDto actualHandleBookAlreadyReturnedExceptionResult = globalExceptionHandler
        .handleBookAlreadyReturnedException(exception);

    verify(exception).getMessage();
    assertEquals("Not all who wander are lost",
        actualHandleBookAlreadyReturnedExceptionResult.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST,
        actualHandleBookAlreadyReturnedExceptionResult.getStatus());
  }

  @Test
  void testHandleBookAlreadyTakenException() {
    ExceptionDto actualHandleBookAlreadyTakenExceptionResult = globalExceptionHandler
        .handleBookAlreadyTakenException(new BookAlreadyTakenException("Isbn"));

    assertEquals("Book with ISBN Isbn already taken!",
        actualHandleBookAlreadyTakenExceptionResult.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, actualHandleBookAlreadyTakenExceptionResult.getStatus());
  }

  @Test
  void testHandleBookAlreadyTakenException2() {
    BookAlreadyTakenException exception = mock(BookAlreadyTakenException.class);
    when(exception.getMessage()).thenReturn("Not all who wander are lost");

    ExceptionDto actualHandleBookAlreadyTakenExceptionResult = globalExceptionHandler
        .handleBookAlreadyTakenException(exception);

    verify(exception).getMessage();
    assertEquals("Not all who wander are lost",
        actualHandleBookAlreadyTakenExceptionResult.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, actualHandleBookAlreadyTakenExceptionResult.getStatus());
  }

  @Test
  void testHandleBookCannotBeDeletedException() {
    ExceptionDto actualHandleBookCannotBeDeletedExceptionResult = globalExceptionHandler
        .handleBookCannotBeDeletedException(new BookCannotBeDeletedException("Isbn"));

    assertEquals("Book with ISBN Isbn was taken. It cannot be deleted!",
        actualHandleBookCannotBeDeletedExceptionResult.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST,
        actualHandleBookCannotBeDeletedExceptionResult.getStatus());
  }

  @Test
  void testHandleBookCannotBeDeletedException2() {
    BookCannotBeDeletedException exception = mock(BookCannotBeDeletedException.class);
    when(exception.getMessage()).thenReturn("Not all who wander are lost");

    ExceptionDto actualHandleBookCannotBeDeletedExceptionResult = globalExceptionHandler
        .handleBookCannotBeDeletedException(exception);

    verify(exception).getMessage();
    assertEquals("Not all who wander are lost",
        actualHandleBookCannotBeDeletedExceptionResult.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST,
        actualHandleBookCannotBeDeletedExceptionResult.getStatus());
  }

  @Test
  void testHandleUnauthorizedException() {
    ExceptionDto actualHandleUnauthorizedExceptionResult = globalExceptionHandler
        .handleUnauthorizedException(new BadCredentialsException("Msg"));

    assertEquals("Msg", actualHandleUnauthorizedExceptionResult.getMessage());
    assertEquals(HttpStatus.UNAUTHORIZED, actualHandleUnauthorizedExceptionResult.getStatus());
  }

  @Test
  void testHandleInternalServerErrorException() {
    ExceptionDto actualHandleInternalServerErrorExceptionResult = globalExceptionHandler
        .handleInternalServerErrorException(new Exception("foo"));

    assertEquals("foo", actualHandleInternalServerErrorExceptionResult.getMessage());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
        actualHandleInternalServerErrorExceptionResult.getStatus());
  }
}
