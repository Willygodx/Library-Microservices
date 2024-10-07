package by.ruslan.project.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.ruslan.project.controller.outer.exception.GlobalExceptionHandler;
import by.ruslan.project.dto.ExceptionDto;
import by.ruslan.project.controller.outer.exception.exceptions.BookNotFoundByIsbnException;
import by.ruslan.project.controller.outer.exception.exceptions.BookWithSameIsbnException;
import by.ruslan.project.controller.outer.exception.validation.Validation;
import by.ruslan.project.controller.outer.exception.validation.ValidationResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.List;
import org.hibernate.validator.internal.engine.path.PathImpl;
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
  void testHandleConstraintViolationException() {
    assertTrue(
        globalExceptionHandler.handleConstraintViolationException(
                new ConstraintViolationException(new HashSet<>()))
            .getValidationList()
            .isEmpty());
  }

  @Test
  void testHandleConstraintViolationException2() {
    ConstraintViolation<Object> constraintViolation = mock(ConstraintViolation.class);
    when(constraintViolation.getPropertyPath()).thenReturn(PathImpl.createRootPath());
    when(constraintViolation.getMessage()).thenReturn("Not all who wander are lost");

    HashSet<ConstraintViolation<?>> constraintViolations = new HashSet<>();
    constraintViolations.add(constraintViolation);

    ValidationResponse actualHandleConstraintViolationExceptionResult = globalExceptionHandler
        .handleConstraintViolationException(new ConstraintViolationException(constraintViolations));

    verify(constraintViolation, atLeast(1)).getMessage();
    verify(constraintViolation, atLeast(1)).getPropertyPath();
    List<Validation> validationList =
        actualHandleConstraintViolationExceptionResult.getValidationList();
    assertEquals(1, validationList.size());
    Validation getResult = validationList.get(0);
    assertEquals("", getResult.getName());
    assertEquals("Not all who wander are lost", getResult.getMessage());
  }

  @Test
  void testHandleConstraintViolationException3() {
    ConstraintViolation<Object> constraintViolation = mock(ConstraintViolation.class);
    when(constraintViolation.getPropertyPath()).thenReturn(PathImpl.createRootPath());
    when(constraintViolation.getMessage()).thenReturn("Not all who wander are lost");
    ConstraintViolation<Object> constraintViolation2 = mock(ConstraintViolation.class);
    when(constraintViolation2.getPropertyPath()).thenReturn(PathImpl.createRootPath());
    when(constraintViolation2.getMessage()).thenReturn("Not all who wander are lost");

    HashSet<ConstraintViolation<?>> constraintViolations = new HashSet<>();
    constraintViolations.add(constraintViolation2);
    constraintViolations.add(constraintViolation);

    ValidationResponse actualHandleConstraintViolationExceptionResult = globalExceptionHandler
        .handleConstraintViolationException(new ConstraintViolationException(constraintViolations));

    verify(constraintViolation2, atLeast(1)).getMessage();
    verify(constraintViolation, atLeast(1)).getMessage();
    verify(constraintViolation2, atLeast(1)).getPropertyPath();
    verify(constraintViolation, atLeast(1)).getPropertyPath();
    List<Validation> validationList =
        actualHandleConstraintViolationExceptionResult.getValidationList();
    assertEquals(2, validationList.size());
    Validation getResult = validationList.get(0);
    assertEquals("", getResult.getName());
    Validation getResult2 = validationList.get(1);
    assertEquals("", getResult2.getName());
    assertEquals("Not all who wander are lost", getResult.getMessage());
    assertEquals("Not all who wander are lost", getResult2.getMessage());
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
