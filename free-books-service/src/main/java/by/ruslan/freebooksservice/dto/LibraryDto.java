package by.ruslan.freebooksservice.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibraryDto {

  private String isbn;

  private LocalDateTime borrowedAt;

  private LocalDateTime returnBy;

}
