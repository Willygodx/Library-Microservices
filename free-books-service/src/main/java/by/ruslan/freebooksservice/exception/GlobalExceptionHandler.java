package by.ruslan.freebooksservice.exception;

import by.ruslan.freebooksservice.dto.ExceptionDto;
import by.ruslan.freebooksservice.exception.exceptions.BookAlreadyReturnedException;
import by.ruslan.freebooksservice.exception.exceptions.BookAlreadyTakenException;
import by.ruslan.freebooksservice.exception.exceptions.BookCannotBeDeletedException;
import by.ruslan.freebooksservice.exception.exceptions.BookNotFoundByIsbnException;
import by.ruslan.freebooksservice.exception.exceptions.BookWithSameIsbnException;
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

  @ExceptionHandler(BookAlreadyReturnedException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ExceptionDto handleBookAlreadyReturnedException(BookAlreadyReturnedException exception) {
    return new ExceptionDto(HttpStatus.BAD_REQUEST, exception.getMessage());
  }

  @ExceptionHandler(BookAlreadyTakenException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ExceptionDto handleBookAlreadyTakenException(BookAlreadyTakenException exception) {
    return new ExceptionDto(HttpStatus.BAD_REQUEST, exception.getMessage());
  }

  @ExceptionHandler(BookCannotBeDeletedException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ExceptionDto handleBookCannotBeDeletedException(BookCannotBeDeletedException exception) {
    return new ExceptionDto(HttpStatus.BAD_REQUEST, exception.getMessage());
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
