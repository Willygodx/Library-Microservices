package by.ruslan.project.service.impl;

import by.ruslan.project.controller.outer.LibraryFeignClient;
import by.ruslan.project.dto.BookDto;
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
    return bookMapper.toDto(bookRepository.findBookById(id).orElse(null));
  }

  @Override
  public BookDto getBookByIsbn(String isbn) {
    return bookMapper.toDto(bookRepository.findBookByIsbn(isbn).orElse(null));
  }

  @Override
  public void createBook(BookDto bookDto) {
    Book newBook = bookRepository.save(bookMapper.toEntity(bookDto));
    libraryFeignClient.addBook(newBook.getId());
  }

  @Override
  public void updateBook(Long id, BookDto bookDto) {
    Book existingBook = bookRepository.findById(id).orElse(null);

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
  public void deleteBook(Long id) {
    bookRepository.deleteById(id);
  }

  @Override
  public List<BookDto> getBooksByIds(List<Long> ids) {
    return bookMapper.toListDto(bookRepository.findBookByIdIn(ids));
  }
}
