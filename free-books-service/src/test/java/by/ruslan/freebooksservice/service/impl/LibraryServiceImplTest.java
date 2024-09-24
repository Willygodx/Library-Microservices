package by.ruslan.freebooksservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.ruslan.freebooksservice.controller.outer.LibraryFeignClient;
import by.ruslan.freebooksservice.dto.BookDto;
import by.ruslan.freebooksservice.dto.LibraryDto;
import by.ruslan.freebooksservice.exception.exceptions.BookAlreadyTakenException;
import by.ruslan.freebooksservice.exception.exceptions.BookCannotBeDeletedException;
import by.ruslan.freebooksservice.exception.exceptions.BookNotFoundByIdException;
import by.ruslan.freebooksservice.exception.exceptions.BookWithSameIdException;
import by.ruslan.freebooksservice.mapper.LibraryMapper;
import by.ruslan.freebooksservice.model.Library;
import by.ruslan.freebooksservice.repository.LibraryRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {LibraryServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class LibraryServiceImplTest {
  @MockBean
  private LibraryFeignClient libraryFeignClient;

  @MockBean
  private LibraryMapper libraryMapper;

  @MockBean
  private LibraryRepository libraryRepository;

  @Autowired
  private LibraryServiceImpl libraryServiceImpl;

  @Test
  void testAddBook() {
    when(libraryRepository.existsById(Mockito.<Long>any())).thenReturn(true);

    assertThrows(BookWithSameIdException.class, () -> libraryServiceImpl.addBook(1L));
    verify(libraryRepository).existsById(eq(1L));
  }

  @Test
  void testAddBook2() {
    Library library = new Library();
    library.setBookId(1L);
    library.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    library.setId(1L);
    library.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(libraryRepository.existsById(Mockito.<Long>any())).thenReturn(false);
    when(libraryRepository.save(Mockito.<Library>any())).thenReturn(library);

    LibraryDto libraryDto = new LibraryDto();
    libraryDto.setBookId(1L);
    libraryDto.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    libraryDto.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(libraryMapper.toDto(Mockito.<Library>any())).thenReturn(libraryDto);

    LibraryDto actualAddBookResult = libraryServiceImpl.addBook(1L);

    verify(libraryMapper).toDto(isA(Library.class));
    verify(libraryRepository).existsById(eq(1L));
    verify(libraryRepository).save(isA(Library.class));
    assertSame(libraryDto, actualAddBookResult);
  }

  @Test
  void testAddBook3() {
    Library library = new Library();
    library.setBookId(1L);
    library.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    library.setId(1L);
    library.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(libraryRepository.existsById(Mockito.<Long>any())).thenReturn(false);
    when(libraryRepository.save(Mockito.<Library>any())).thenReturn(library);
    when(libraryMapper.toDto(Mockito.<Library>any())).thenThrow(new BookWithSameIdException(1L));

    assertThrows(BookWithSameIdException.class, () -> libraryServiceImpl.addBook(1L));
    verify(libraryMapper).toDto(isA(Library.class));
    verify(libraryRepository).existsById(eq(1L));
    verify(libraryRepository).save(isA(Library.class));
  }

  @Test
  void testUpdateBook() {
    Library library = new Library();
    library.setBookId(1L);
    library.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    library.setId(1L);
    library.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    Optional<Library> ofResult = Optional.of(library);
    when(libraryRepository.findByBookId(Mockito.<Long>any())).thenReturn(ofResult);

    assertThrows(BookAlreadyTakenException.class, () -> libraryServiceImpl.updateBook(1L));
    verify(libraryRepository).findByBookId(eq(1L));
  }

  @Test
  void testUpdateBook2() {
    Library library = mock(Library.class);
    when(library.getBorrowedAt()).thenThrow(new BookWithSameIdException(1L));
    when(library.getReturnBy()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    doNothing().when(library).setBookId(Mockito.<Long>any());
    doNothing().when(library).setBorrowedAt(Mockito.<LocalDateTime>any());
    doNothing().when(library).setId(Mockito.<Long>any());
    doNothing().when(library).setReturnBy(Mockito.<LocalDateTime>any());
    library.setBookId(1L);
    library.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    library.setId(1L);
    library.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    Optional<Library> ofResult = Optional.of(library);
    when(libraryRepository.findByBookId(Mockito.<Long>any())).thenReturn(ofResult);

    assertThrows(BookWithSameIdException.class, () -> libraryServiceImpl.updateBook(1L));
    verify(library).getBorrowedAt();
    verify(library).getReturnBy();
    verify(library).setBookId(eq(1L));
    verify(library).setBorrowedAt(isA(LocalDateTime.class));
    verify(library).setId(eq(1L));
    verify(library).setReturnBy(isA(LocalDateTime.class));
    verify(libraryRepository).findByBookId(eq(1L));
  }

  @Test
  void testUpdateBook3() {
    Optional<Library> emptyResult = Optional.empty();
    when(libraryRepository.findByBookId(Mockito.<Long>any())).thenReturn(emptyResult);

    assertThrows(BookNotFoundByIdException.class, () -> libraryServiceImpl.updateBook(1L));
    verify(libraryRepository).findByBookId(eq(1L));
  }

  @Test
  void testGetFreeBooks() {
    when(libraryRepository.findAvailableBooks()).thenReturn(new ArrayList<>());
    ArrayList<BookDto> bookDtoList = new ArrayList<>();
    when(libraryFeignClient.getBooksByIds(Mockito.<List<Long>>any())).thenReturn(bookDtoList);

    List<BookDto> actualFreeBooks = libraryServiceImpl.getFreeBooks();

    verify(libraryFeignClient).getBooksByIds(isA(List.class));
    verify(libraryRepository).findAvailableBooks();
    assertTrue(actualFreeBooks.isEmpty());
    assertSame(bookDtoList, actualFreeBooks);
  }

  @Test
  void testGetFreeBooks2() {
    when(libraryRepository.findAvailableBooks()).thenReturn(new ArrayList<>());
    when(libraryFeignClient.getBooksByIds(Mockito.<List<Long>>any())).thenThrow(
        new BookWithSameIdException(1L));

    assertThrows(BookWithSameIdException.class, () -> libraryServiceImpl.getFreeBooks());
    verify(libraryFeignClient).getBooksByIds(isA(List.class));
    verify(libraryRepository).findAvailableBooks();
  }

  @Test
  void testDeleteBook() throws Exception {
    Library library = new Library();
    library.setBookId(1L);
    library.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    library.setId(1L);
    library.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    Optional<Library> ofResult = Optional.of(library);
    when(libraryRepository.findByBookId(Mockito.<Long>any())).thenReturn(ofResult);

    assertThrows(BookCannotBeDeletedException.class, () -> libraryServiceImpl.deleteBook(1L));
    verify(libraryRepository).findByBookId(eq(1L));
  }

  @Test
  void testDeleteBook2() throws Exception {
    Library library = mock(Library.class);
    when(library.getReturnBy()).thenThrow(new BookWithSameIdException(1L));
    when(library.getBorrowedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    doNothing().when(library).setBookId(Mockito.<Long>any());
    doNothing().when(library).setBorrowedAt(Mockito.<LocalDateTime>any());
    doNothing().when(library).setId(Mockito.<Long>any());
    doNothing().when(library).setReturnBy(Mockito.<LocalDateTime>any());
    library.setBookId(1L);
    library.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    library.setId(1L);
    library.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    Optional<Library> ofResult = Optional.of(library);
    when(libraryRepository.findByBookId(Mockito.<Long>any())).thenReturn(ofResult);

    assertThrows(BookWithSameIdException.class, () -> libraryServiceImpl.deleteBook(1L));
    verify(library).getBorrowedAt();
    verify(library).getReturnBy();
    verify(library).setBookId(eq(1L));
    verify(library).setBorrowedAt(isA(LocalDateTime.class));
    verify(library).setId(eq(1L));
    verify(library).setReturnBy(isA(LocalDateTime.class));
    verify(libraryRepository).findByBookId(eq(1L));
  }

  @Test
  void testDeleteBook3() throws Exception {
    Optional<Library> emptyResult = Optional.empty();
    when(libraryRepository.findByBookId(Mockito.<Long>any())).thenReturn(emptyResult);

    assertThrows(BookNotFoundByIdException.class, () -> libraryServiceImpl.deleteBook(1L));
    verify(libraryRepository).findByBookId(eq(1L));
  }

  @Test
  void testReturnBook() {
    Library library = new Library();
    library.setBookId(1L);
    library.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    library.setId(1L);
    library.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    Optional<Library> ofResult = Optional.of(library);

    Library library2 = new Library();
    library2.setBookId(1L);
    library2.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    library2.setId(1L);
    library2.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(libraryRepository.save(Mockito.<Library>any())).thenReturn(library2);
    when(libraryRepository.findByBookId(Mockito.<Long>any())).thenReturn(ofResult);

    LibraryDto libraryDto = new LibraryDto();
    libraryDto.setBookId(1L);
    libraryDto.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    libraryDto.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(libraryMapper.toDto(Mockito.<Library>any())).thenReturn(libraryDto);

    LibraryDto actualReturnBookResult = libraryServiceImpl.returnBook(1L);

    verify(libraryMapper).toDto(isA(Library.class));
    verify(libraryRepository).findByBookId(eq(1L));
    verify(libraryRepository).save(isA(Library.class));
    assertSame(libraryDto, actualReturnBookResult);
  }

  @Test
  void testReturnBook2() {
    Library library = new Library();
    library.setBookId(1L);
    library.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    library.setId(1L);
    library.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    Optional<Library> ofResult = Optional.of(library);

    Library library2 = new Library();
    library2.setBookId(1L);
    library2.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    library2.setId(1L);
    library2.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(libraryRepository.save(Mockito.<Library>any())).thenReturn(library2);
    when(libraryRepository.findByBookId(Mockito.<Long>any())).thenReturn(ofResult);
    when(libraryMapper.toDto(Mockito.<Library>any())).thenThrow(new BookWithSameIdException(1L));

    assertThrows(BookWithSameIdException.class, () -> libraryServiceImpl.returnBook(1L));
    verify(libraryMapper).toDto(isA(Library.class));
    verify(libraryRepository).findByBookId(eq(1L));
    verify(libraryRepository).save(isA(Library.class));
  }

  @Test
  void testReturnBook3() {
    Optional<Library> emptyResult = Optional.empty();
    when(libraryRepository.findByBookId(Mockito.<Long>any())).thenReturn(emptyResult);

    assertThrows(BookNotFoundByIdException.class, () -> libraryServiceImpl.returnBook(1L));
    verify(libraryRepository).findByBookId(eq(1L));
  }
}
