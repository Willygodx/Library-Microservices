package by.ruslan.freebooksservice.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryDto {

  private Long bookId;

  private LocalDateTime borrowedAt;

  private LocalDateTime returnBy;

}
