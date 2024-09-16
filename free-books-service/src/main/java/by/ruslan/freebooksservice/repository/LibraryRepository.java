package by.ruslan.freebooksservice.repository;

import by.ruslan.freebooksservice.model.Library;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {
  Optional<Library> findByBookId(Long bookId);

  @Query("SELECT lb.bookId FROM Library lb WHERE lb.returnBy <= CURRENT_DATE OR lb.borrowedAt IS NULL")
  List<Long> findAvailableBooks();

}
