package by.ruslan.project.controller.outer.exception;

import by.ruslan.project.dto.ExceptionDto;
import by.ruslan.project.controller.outer.exception.exceptions.BookNotFoundByIsbnException;
import by.ruslan.project.controller.outer.exception.exceptions.BookWithSameIsbnException;
import by.ruslan.project.controller.outer.exception.validation.Validation;
import by.ruslan.project.controller.outer.exception.validation.ValidationResponse;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BookNotFoundByIsbnException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ExceptionDto handleBookNotFoundByIsbnException(BookNotFoundByIsbnException exception) {
    return new ExceptionDto(HttpStatus.NOT_FOUND, exception.getMessage());
  }

  @ExceptionHandler(BookWithSameIsbnException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public ExceptionDto handleBookWithSameIsbnException(BookWithSameIsbnException exception) {
    return new ExceptionDto(HttpStatus.CONFLICT, exception.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ValidationResponse handleConstraintViolationException(
      ConstraintViolationException exception) {
    final List<Validation> validationList = exception.getConstraintViolations().stream()
        .map(validation -> new Validation(validation.getPropertyPath().toString(),
            validation.getMessage()))
        .toList();

    return new ValidationResponse(validationList);
  }

  @ExceptionHandler()
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ResponseBody
  public ExceptionDto handleUnauthorizedException(BadCredentialsException exception) {
    return new ExceptionDto(HttpStatus.UNAUTHORIZED, exception.getMessage());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ExceptionDto handleInternalServerErrorException(Exception exception) {
    return new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
  }
}
