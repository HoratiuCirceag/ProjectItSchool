package movie.service;

import movie.controller.model.MovieDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MovieService {
    List<MovieDTO> findByKeyword(String searchQuery);
    void save(MovieDTO movieDTO);

    Optional<MovieDTO> findById(Integer id);

    void deleteMovie(Integer id);

    List<MovieDTO> getAllMovies();

    List<MovieDTO> findAllByIds(List<Integer> movieIds);

}
