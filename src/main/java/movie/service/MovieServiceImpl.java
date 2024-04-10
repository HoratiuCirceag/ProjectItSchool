package movie.service;

import lombok.RequiredArgsConstructor;
import movie.controller.model.ActorDTO;
import movie.controller.model.MovieDTO;
import movie.entity.Actor;
import movie.entity.Movie;
import movie.mapper.ActorMapper;
import movie.mapper.MovieMapper;
import movie.repository.ActorRepository;
import movie.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final MovieMapper movieMapper;
    private final ActorMapper actorMapper;

    @Override
    public List<MovieDTO> findByKeyword(String searchQuery) {
        if (searchQuery==null){
            return movieRepository.findAll().stream().map(movieMapper::entityToDTO).toList();
        }
        List<Movie> searchResults=movieRepository.findByKeyword(searchQuery);
        return searchResults.stream().map(movieMapper::entityToDTO).toList();
    }

    @Override
    public void save(MovieDTO movieDTO) {
        List<Actor> actors=new ArrayList<>();
        if (movieDTO.getActorDTOList() != null) {
            for (ActorDTO actor:movieDTO.getActorDTOList()) {
                if (actor!=null && actor.getId() !=null){
                    actors.add(actorRepository.findById(actor.getId()).orElseThrow(()->new IllegalArgumentException("Invalid Actor Id:" + actor.getId())));
                }
            }
        }
        Movie movie=movieMapper.DTOToEntity(movieDTO);
        movie.setActorList(actors);
        movieRepository.save(movie);
    }

    @Override
    public Optional<MovieDTO> findById(Integer id) {
        Optional<Movie> optionalMovie=movieRepository.findById(id);
        Optional<MovieDTO> movieDTO=optionalMovie.map(movieMapper::entityToDTO);
        List<ActorDTO> actorDTOList=optionalMovie.get().getActorList().stream().map(actorMapper::entityToDTO).toList();
        movieDTO.get().setActorDTOList(actorDTOList);
        return movieDTO;
    }

    @Override
    public void deleteMovie(Integer id) {
        movieRepository.deleteById(id);
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream().map(movieMapper::entityToDTO).toList();
    }

    @Override
    public List<MovieDTO> findAllByIds(List<Integer> movieIds) {
        List<Movie> movies=movieRepository.findAllById(movieIds);
        return movies.stream().map(movieMapper::entityToDTO).toList();
    }
}
