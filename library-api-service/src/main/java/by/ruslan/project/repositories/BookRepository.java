package by.ruslan.project.repositories;

import by.ruslan.project.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
  Optional<Book> findBookByIsbn(String isbn);

  List<Book> findBookByIsbnIn(List<String> isbns);

  Boolean existsByIsbn(String isbn);

  void deleteByIsbn(String isbn);

}
