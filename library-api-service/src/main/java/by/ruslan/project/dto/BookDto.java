package by.ruslan.project.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

  @Pattern(message = "Invalid ISBN-13 format",
      regexp = "^978-[0-9]{1}-[0-9]{3}-[0-9]{5}-[0-9]$")
  private String isbn;

  @NotEmpty(message = "Book name can't be empty", groups = {Marker.OnCreate.class,
      Marker.OnUpdate.class})
  private String name;

  @NotEmpty(message = "Genre can't be empty", groups = {Marker.OnCreate.class,
      Marker.OnUpdate.class})
  private String genre;

  @NotEmpty(message = "Description can't be empty", groups = {Marker.OnCreate.class,
      Marker.OnUpdate.class})
  private String description;

  @NotEmpty(message = "Author can't be empty", groups = {Marker.OnCreate.class,
      Marker.OnUpdate.class})
  private String author;

}
