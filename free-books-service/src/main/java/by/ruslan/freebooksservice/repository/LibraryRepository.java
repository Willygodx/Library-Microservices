package by.ruslan.freebooksservice.repository;

import by.ruslan.freebooksservice.model.Library;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<Library, String> {
  @Query("SELECT lb.isbn FROM Library lb WHERE lb.returnBy <= CURRENT_DATE OR lb.borrowedAt IS NULL")
  List<String> findAvailableBooks();

  boolean existsByIsbn(String isbn);

  Optional<Library> findByIsbn(String isbn);

  void deleteByIsbn(String isbn);
}
