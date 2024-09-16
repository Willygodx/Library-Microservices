package by.ruslan.freebooksservice.mapper;

import by.ruslan.freebooksservice.dto.LibraryDto;
import by.ruslan.freebooksservice.model.Library;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface LibraryMapper {
  LibraryDto toDto(Library library);

  Library toEntity(LibraryDto libraryDto);

  List<LibraryDto> toListDto(List<Library> libraries);

}
