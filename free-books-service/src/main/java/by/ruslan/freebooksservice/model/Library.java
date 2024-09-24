package by.ruslan.freebooksservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "free_books")
public class Library {
  @Id
  @Column(name = "isbn")
  private String isbn;

  @Column(name = "borrowed_at")
  private LocalDateTime borrowedAt;

  @Column(name = "return_by")
  private LocalDateTime returnBy;
}
