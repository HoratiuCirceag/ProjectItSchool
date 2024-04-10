package movie.repository;

import movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Integer> {
    @Query("SELECT m FROM Movie m WHERE " +
            "LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<Movie> findByKeyword(@Param("keyword") String keyword);
}
