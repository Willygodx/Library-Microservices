package by.ruslan.project.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.ruslan.project.controller.outer.LibraryFeignClient;
import by.ruslan.project.dto.BookDto;
import by.ruslan.project.exception.exceptions.BookNotFoundByIdException;
import by.ruslan.project.exception.exceptions.BookNotFoundByIsbnException;
import by.ruslan.project.exception.exceptions.BookWithSameIsbnException;
import by.ruslan.project.mapper.BookMapper;
import by.ruslan.project.model.Book;
import by.ruslan.project.repositories.BookRepository;
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

@ContextConfiguration(classes = {BookServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BookServiceImplTest {
  @MockBean
  private BookMapper bookMapper;

  @MockBean
  private BookRepository bookRepository;

  @Autowired
  private BookServiceImpl bookServiceImpl;

  @MockBean
  private LibraryFeignClient libraryFeignClient;

  @Test
  void testGetAllBooks() {
    when(bookRepository.findAll()).thenReturn(new ArrayList<>());
    ArrayList<BookDto> bookDtoList = new ArrayList<>();
    when(bookMapper.toListDto(Mockito.<List<Book>>any())).thenReturn(bookDtoList);

    List<BookDto> actualAllBooks = bookServiceImpl.getAllBooks();

    verify(bookMapper).toListDto(isA(List.class));
    verify(bookRepository).findAll();
    assertTrue(actualAllBooks.isEmpty());
    assertSame(bookDtoList, actualAllBooks);
  }

  @Test
  void testGetAllBooks2() {
    when(bookRepository.findAll()).thenReturn(new ArrayList<>());
    when(bookMapper.toListDto(Mockito.<List<Book>>any())).thenThrow(
        new BookWithSameIsbnException("Isbn"));

    assertThrows(BookWithSameIsbnException.class, () -> bookServiceImpl.getAllBooks());
    verify(bookMapper).toListDto(isA(List.class));
    verify(bookRepository).findAll();
  }

  @Test
  void testGetBookById() {
    Book book = new Book();
    book.setAuthor("JaneDoe");
    book.setDescription("The characteristics of someone or something");
    book.setGenre("Genre");
    book.setId(1L);
    book.setIsbn("Isbn");
    book.setName("Name");
    Optional<Book> ofResult = Optional.of(book);
    when(bookRepository.findBookById(Mockito.<Long>any())).thenReturn(ofResult);

    BookDto bookDto = new BookDto();
    bookDto.setAuthor("JaneDoe");
    bookDto.setDescription("The characteristics of someone or something");
    bookDto.setGenre("Genre");
    bookDto.setIsbn("Isbn");
    bookDto.setName("Name");
    when(bookMapper.toDto(Mockito.<Book>any())).thenReturn(bookDto);

    BookDto actualBookById = bookServiceImpl.getBookById(1L);

    verify(bookMapper).toDto(isA(Book.class));
    verify(bookRepository).findBookById(eq(1L));
    assertSame(bookDto, actualBookById);
  }

  @Test
  void testGetBookById2() {
    Book book = new Book();
    book.setAuthor("JaneDoe");
    book.setDescription("The characteristics of someone or something");
    book.setGenre("Genre");
    book.setId(1L);
    book.setIsbn("Isbn");
    book.setName("Name");
    Optional<Book> ofResult = Optional.of(book);
    when(bookRepository.findBookById(Mockito.<Long>any())).thenReturn(ofResult);
    when(bookMapper.toDto(Mockito.<Book>any())).thenThrow(new BookWithSameIsbnException("Isbn"));

    assertThrows(BookWithSameIsbnException.class, () -> bookServiceImpl.getBookById(1L));
    verify(bookMapper).toDto(isA(Book.class));
    verify(bookRepository).findBookById(eq(1L));
  }

  @Test
  void testGetBookById3() {
    Optional<Book> emptyResult = Optional.empty();
    when(bookRepository.findBookById(Mockito.<Long>any())).thenReturn(emptyResult);

    assertThrows(BookNotFoundByIdException.class, () -> bookServiceImpl.getBookById(1L));
    verify(bookRepository).findBookById(eq(1L));
  }

  @Test
  void testGetBookByIsbn() {
    Book book = new Book();
    book.setAuthor("JaneDoe");
    book.setDescription("The characteristics of someone or something");
    book.setGenre("Genre");
    book.setId(1L);
    book.setIsbn("Isbn");
    book.setName("Name");
    Optional<Book> ofResult = Optional.of(book);
    when(bookRepository.findBookByIsbn(Mockito.<String>any())).thenReturn(ofResult);

    BookDto bookDto = new BookDto();
    bookDto.setAuthor("JaneDoe");
    bookDto.setDescription("The characteristics of someone or something");
    bookDto.setGenre("Genre");
    bookDto.setIsbn("Isbn");
    bookDto.setName("Name");
    when(bookMapper.toDto(Mockito.<Book>any())).thenReturn(bookDto);

    BookDto actualBookByIsbn = bookServiceImpl.getBookByIsbn("Isbn");

    verify(bookMapper).toDto(isA(Book.class));
    verify(bookRepository).findBookByIsbn(eq("Isbn"));
    assertSame(bookDto, actualBookByIsbn);
  }

  @Test
  void testGetBookByIsbn2() {
    Book book = new Book();
    book.setAuthor("JaneDoe");
    book.setDescription("The characteristics of someone or something");
    book.setGenre("Genre");
    book.setId(1L);
    book.setIsbn("Isbn");
    book.setName("Name");
    Optional<Book> ofResult = Optional.of(book);
    when(bookRepository.findBookByIsbn(Mockito.<String>any())).thenReturn(ofResult);
    when(bookMapper.toDto(Mockito.<Book>any())).thenThrow(new BookWithSameIsbnException("Isbn"));

    assertThrows(BookWithSameIsbnException.class, () -> bookServiceImpl.getBookByIsbn("Isbn"));
    verify(bookMapper).toDto(isA(Book.class));
    verify(bookRepository).findBookByIsbn(eq("Isbn"));
  }

  @Test
  void testGetBookByIsbn3() {
    Optional<Book> emptyResult = Optional.empty();
    when(bookRepository.findBookByIsbn(Mockito.<String>any())).thenReturn(emptyResult);

    assertThrows(BookNotFoundByIsbnException.class, () -> bookServiceImpl.getBookByIsbn("Isbn"));
    verify(bookRepository).findBookByIsbn(eq("Isbn"));
  }

  @Test
  void testCreateBook() {
    when(bookRepository.existsByIsbn(Mockito.<String>any())).thenReturn(true);

    BookDto bookDto = new BookDto();
    bookDto.setAuthor("JaneDoe");
    bookDto.setDescription("The characteristics of someone or something");
    bookDto.setGenre("Genre");
    bookDto.setIsbn("Isbn");
    bookDto.setName("Name");

    assertThrows(BookWithSameIsbnException.class, () -> bookServiceImpl.createBook(bookDto));
    verify(bookRepository).existsByIsbn(eq("Isbn"));
  }

  @Test
  void testCreateBook2() {
    Book book = new Book();
    book.setAuthor("JaneDoe");
    book.setDescription("The characteristics of someone or something");
    book.setGenre("Genre");
    book.setId(1L);
    book.setIsbn("Isbn");
    book.setName("Name");
    when(bookRepository.existsByIsbn(Mockito.<String>any())).thenReturn(false);
    when(bookRepository.save(Mockito.<Book>any())).thenReturn(book);

    Book book2 = new Book();
    book2.setAuthor("JaneDoe");
    book2.setDescription("The characteristics of someone or something");
    book2.setGenre("Genre");
    book2.setId(1L);
    book2.setIsbn("Isbn");
    book2.setName("Name");
    when(bookMapper.toEntity(Mockito.<BookDto>any())).thenReturn(book2);
    doNothing().when(libraryFeignClient).addBook(Mockito.<Long>any());

    BookDto bookDto = new BookDto();
    bookDto.setAuthor("JaneDoe");
    bookDto.setDescription("The characteristics of someone or something");
    bookDto.setGenre("Genre");
    bookDto.setIsbn("Isbn");
    bookDto.setName("Name");

    bookServiceImpl.createBook(bookDto);

    verify(libraryFeignClient).addBook(eq(1L));
    verify(bookMapper).toEntity(isA(BookDto.class));
    verify(bookRepository).existsByIsbn(eq("Isbn"));
    verify(bookRepository).save(isA(Book.class));
  }

  @Test
  void testCreateBook3() {
    Book book = new Book();
    book.setAuthor("JaneDoe");
    book.setDescription("The characteristics of someone or something");
    book.setGenre("Genre");
    book.setId(1L);
    book.setIsbn("Isbn");
    book.setName("Name");
    when(bookRepository.existsByIsbn(Mockito.<String>any())).thenReturn(false);
    when(bookRepository.save(Mockito.<Book>any())).thenReturn(book);

    Book book2 = new Book();
    book2.setAuthor("JaneDoe");
    book2.setDescription("The characteristics of someone or something");
    book2.setGenre("Genre");
    book2.setId(1L);
    book2.setIsbn("Isbn");
    book2.setName("Name");
    when(bookMapper.toEntity(Mockito.<BookDto>any())).thenReturn(book2);
    doThrow(new BookWithSameIsbnException("Isbn")).when(libraryFeignClient)
        .addBook(Mockito.<Long>any());

    BookDto bookDto = new BookDto();
    bookDto.setAuthor("JaneDoe");
    bookDto.setDescription("The characteristics of someone or something");
    bookDto.setGenre("Genre");
    bookDto.setIsbn("Isbn");
    bookDto.setName("Name");

    assertThrows(BookWithSameIsbnException.class, () -> bookServiceImpl.createBook(bookDto));
    verify(libraryFeignClient).addBook(eq(1L));
    verify(bookMapper).toEntity(isA(BookDto.class));
    verify(bookRepository).existsByIsbn(eq("Isbn"));
    verify(bookRepository).save(isA(Book.class));
  }

  @Test
  void testUpdateBook() {
    Book book = new Book();
    book.setAuthor("JaneDoe");
    book.setDescription("The characteristics of someone or something");
    book.setGenre("Genre");
    book.setId(1L);
    book.setIsbn("Isbn");
    book.setName("Name");
    Optional<Book> ofResult = Optional.of(book);

    Book book2 = new Book();
    book2.setAuthor("JaneDoe");
    book2.setDescription("The characteristics of someone or something");
    book2.setGenre("Genre");
    book2.setId(1L);
    book2.setIsbn("Isbn");
    book2.setName("Name");
    when(bookRepository.save(Mockito.<Book>any())).thenReturn(book2);
    when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    BookDto bookDto = new BookDto();
    bookDto.setAuthor("JaneDoe");
    bookDto.setDescription("The characteristics of someone or something");
    bookDto.setGenre("Genre");
    bookDto.setIsbn("Isbn");
    bookDto.setName("Name");

    bookServiceImpl.updateBook(1L, bookDto);

    verify(bookRepository).findById(eq(1L));
    verify(bookRepository).save(isA(Book.class));
  }

  @Test
  void testUpdateBook2() {
    Book book = new Book();
    book.setAuthor("JaneDoe");
    book.setDescription("The characteristics of someone or something");
    book.setGenre("Genre");
    book.setId(1L);
    book.setIsbn("Isbn");
    book.setName("Name");
    Optional<Book> ofResult = Optional.of(book);
    when(bookRepository.save(Mockito.<Book>any())).thenThrow(new BookWithSameIsbnException("Isbn"));
    when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    BookDto bookDto = new BookDto();
    bookDto.setAuthor("JaneDoe");
    bookDto.setDescription("The characteristics of someone or something");
    bookDto.setGenre("Genre");
    bookDto.setIsbn("Isbn");
    bookDto.setName("Name");

    assertThrows(BookWithSameIsbnException.class, () -> bookServiceImpl.updateBook(1L, bookDto));
    verify(bookRepository).findById(eq(1L));
    verify(bookRepository).save(isA(Book.class));
  }

  @Test
  void testUpdateBook3() {
    Optional<Book> emptyResult = Optional.empty();
    when(bookRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

    BookDto bookDto = new BookDto();
    bookDto.setAuthor("JaneDoe");
    bookDto.setDescription("The characteristics of someone or something");
    bookDto.setGenre("Genre");
    bookDto.setIsbn("Isbn");
    bookDto.setName("Name");

    assertThrows(BookNotFoundByIdException.class, () -> bookServiceImpl.updateBook(1L, bookDto));
    verify(bookRepository).findById(eq(1L));
  }

  @Test
  void testDeleteBook() throws Exception {
    doNothing().when(bookRepository).deleteById(Mockito.<Long>any());
    when(bookRepository.existsById(Mockito.<Long>any())).thenReturn(true);
    doNothing().when(libraryFeignClient).deleteBook(Mockito.<Long>any());

    bookServiceImpl.deleteBook(1L);

    verify(libraryFeignClient).deleteBook(eq(1L));
    verify(bookRepository).deleteById(eq(1L));
    verify(bookRepository).existsById(eq(1L));
  }

  @Test
  void testDeleteBook2() throws Exception {
    when(bookRepository.existsById(Mockito.<Long>any())).thenReturn(true);
    doThrow(new BookWithSameIsbnException("Isbn")).when(libraryFeignClient)
        .deleteBook(Mockito.<Long>any());

    assertThrows(BookWithSameIsbnException.class, () -> bookServiceImpl.deleteBook(1L));
    verify(libraryFeignClient).deleteBook(eq(1L));
    verify(bookRepository).existsById(eq(1L));
  }

  @Test
  void testDeleteBook3() throws Exception {
    when(bookRepository.existsById(Mockito.<Long>any())).thenReturn(false);

    assertThrows(BookNotFoundByIdException.class, () -> bookServiceImpl.deleteBook(1L));
    verify(bookRepository).existsById(eq(1L));
  }

  @Test
  void testGetBooksByIds() {
    when(bookRepository.findBookByIdIn(Mockito.<List<Long>>any())).thenReturn(new ArrayList<>());
    ArrayList<BookDto> bookDtoList = new ArrayList<>();
    when(bookMapper.toListDto(Mockito.<List<Book>>any())).thenReturn(bookDtoList);

    List<BookDto> actualBooksByIds = bookServiceImpl.getBooksByIds(new ArrayList<>());

    verify(bookMapper).toListDto(isA(List.class));
    verify(bookRepository).findBookByIdIn(isA(List.class));
    assertTrue(actualBooksByIds.isEmpty());
    assertSame(bookDtoList, actualBooksByIds);
  }

  @Test
  void testGetBooksByIds2() {
    when(bookRepository.findBookByIdIn(Mockito.<List<Long>>any())).thenReturn(new ArrayList<>());
    when(bookMapper.toListDto(Mockito.<List<Book>>any())).thenThrow(
        new BookWithSameIsbnException("Isbn"));

    assertThrows(BookWithSameIsbnException.class,
        () -> bookServiceImpl.getBooksByIds(new ArrayList<>()));
    verify(bookMapper).toListDto(isA(List.class));
    verify(bookRepository).findBookByIdIn(isA(List.class));
  }

  @Test
  void testGetBooksByIds3() {
    when(bookRepository.findBookByIdIn(Mockito.<List<Long>>any())).thenReturn(new ArrayList<>());
    ArrayList<BookDto> bookDtoList = new ArrayList<>();
    when(bookMapper.toListDto(Mockito.<List<Book>>any())).thenReturn(bookDtoList);

    ArrayList<Long> ids = new ArrayList<>();
    ids.add(1L);

    List<BookDto> actualBooksByIds = bookServiceImpl.getBooksByIds(ids);

    verify(bookMapper).toListDto(isA(List.class));
    verify(bookRepository).findBookByIdIn(isA(List.class));
    assertTrue(actualBooksByIds.isEmpty());
    assertSame(bookDtoList, actualBooksByIds);
  }

  @Test
  void testGetBooksByIds4() {
    when(bookRepository.findBookByIdIn(Mockito.<List<Long>>any())).thenReturn(new ArrayList<>());
    ArrayList<BookDto> bookDtoList = new ArrayList<>();
    when(bookMapper.toListDto(Mockito.<List<Book>>any())).thenReturn(bookDtoList);

    ArrayList<Long> ids = new ArrayList<>();
    ids.add(0L);
    ids.add(1L);

    List<BookDto> actualBooksByIds = bookServiceImpl.getBooksByIds(ids);

    verify(bookMapper).toListDto(isA(List.class));
    verify(bookRepository).findBookByIdIn(isA(List.class));
    assertTrue(actualBooksByIds.isEmpty());
    assertSame(bookDtoList, actualBooksByIds);
  }
}
