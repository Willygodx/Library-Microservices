package by.ruslan.project.service.impl;

import by.ruslan.project.controller.outer.LibraryFeignClient;
import by.ruslan.project.dto.BookDto;
import by.ruslan.project.exception.exceptions.BookNotFoundByIdException;
import by.ruslan.project.exception.exceptions.BookNotFoundByIsbnException;
import by.ruslan.project.exception.exceptions.BookWithSameIsbnException;
import by.ruslan.project.mapper.BookMapper;
import by.ruslan.project.model.Book;
import by.ruslan.project.repositories.BookRepository;
import by.ruslan.project.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  private final BookMapper bookMapper;

  private final LibraryFeignClient libraryFeignClient;

  @Override
  public List<BookDto> getAllBooks() {
    return bookMapper.toListDto(bookRepository.findAll());
  }

  @Override
  public BookDto getBookById(Long id) {
    return bookMapper.toDto(bookRepository.findBookById(id)
        .orElseThrow(() -> new BookNotFoundByIdException(id)));
  }

  @Override
  public BookDto getBookByIsbn(String isbn) {
    return bookMapper.toDto(bookRepository.findBookByIsbn(isbn)
        .orElseThrow(() -> new BookNotFoundByIsbnException(isbn)));
  }

  @Override
  public void createBook(BookDto bookDto) {
    if (bookRepository.existsByIsbn(bookDto.getIsbn())) {
      throw new BookWithSameIsbnException(bookDto.getIsbn());
    } else {
      Book newBook = bookRepository.save(bookMapper.toEntity(bookDto));
      libraryFeignClient.addBook(newBook.getId());
    }
  }

  @Override
  public void updateBook(Long id, BookDto bookDto) {
    Book existingBook = bookRepository.findById(id)
        .orElseThrow(() -> new BookNotFoundByIdException(id));

    if (bookDto.getIsbn() != null) {
      existingBook.setIsbn(bookDto.getIsbn());
    }

    if (bookDto.getName() != null) {
      existingBook.setName(bookDto.getName());
    }

    if (bookDto.getGenre() != null) {
      existingBook.setGenre(bookDto.getGenre());
    }

    if (bookDto.getDescription() != null) {
      existingBook.setDescription(bookDto.getDescription());
    }

    if (bookDto.getAuthor() != null) {
      existingBook.setAuthor(bookDto.getAuthor());
    }

    bookRepository.save(existingBook);
  }

  @Override
  public void deleteBook(Long id) throws Exception {
    if (!bookRepository.existsById(id)) {
      throw new BookNotFoundByIdException(id);
    } else {
      libraryFeignClient.deleteBook(id);
      bookRepository.deleteById(id);
    }
  }

  @Override
  public List<BookDto> getBooksByIds(List<Long> ids) {
    return bookMapper.toListDto(bookRepository.findBookByIdIn(ids));
  }
}
