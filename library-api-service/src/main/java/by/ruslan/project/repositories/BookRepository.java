package by.ruslan.project.repositories;

import by.ruslan.project.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
  Optional<Book> findBookById(Long id);

  Optional<Book> findBookByIsbn(String isbn);

  List<Book> findBookByIdIn(List<Long> ids);

  Boolean existsByIsbn(String isbn);

}
