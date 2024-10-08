package by.ruslan.project.mapper;

import by.ruslan.project.dto.BookDto;
import by.ruslan.project.model.Book;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface BookMapper {
  BookDto toDto(Book book);

  Book toEntity(BookDto bookDto);

  List<BookDto> toListDto(List<Book> books);

  void updateBookFromDto(BookDto bookDto, @MappingTarget Book book);
}