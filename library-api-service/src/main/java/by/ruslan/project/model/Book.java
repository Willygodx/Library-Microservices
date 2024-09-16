package by.ruslan.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "book")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "isbn")
  private String isbn;

  @Column(name = "name")
  private String name;

  @Column(name = "genre")
  private String genre;

  @Column(name = "description")
  private String description;

  @Column(name = "author")
  private String author;

  @Override
  public boolean equals(Object other) {
    return other instanceof Book
        && ((Book) other).getIsbn().equals(isbn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isbn);
  }
}
