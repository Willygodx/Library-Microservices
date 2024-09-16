package by.ruslan.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

  private String isbn;

  private String name;

  private String genre;

  private String description;

  private String author;
}
