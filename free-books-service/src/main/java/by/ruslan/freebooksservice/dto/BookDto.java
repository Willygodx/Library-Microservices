package by.ruslan.freebooksservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookDto {
  private String isbn;

  private String name;

  private String genre;

  private String description;

  private String author;
}

