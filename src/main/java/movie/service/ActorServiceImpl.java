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
public class ActorServiceImpl implements ActorService{
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final ActorMapper actorMapper;
    private final MovieMapper movieMapper;

    @Override
    public List<ActorDTO> findByKeyword(String searchQuery) {
        if (searchQuery==null){
            return actorRepository.findAll().stream().map(actorMapper::entityToDTO).toList();
        }
        List<Actor> searchResults=actorRepository.findByKeyword(searchQuery);
        return searchResults.stream().map(actorMapper::entityToDTO).toList();
    }

    @Override
    public void save(ActorDTO actorDTO,List<Integer> movieIds) {
        Actor actor=actorMapper.DTOToEntity(actorDTO);
        Actor savedActor=actorRepository.save(actor);
        if (movieIds!=null){
            List<Movie> movies=movieRepository.findAllById(movieIds);
            for (Movie movie:movies) {
                movie.getActorList().add(savedActor);
                movieRepository.save(movie);
            }
        }
    }

    @Override
    public Optional<ActorDTO> findById(Integer id) {
        Optional<Actor> optionalActor=actorRepository.findById(id);
        Optional<ActorDTO> actorDTO = optionalActor.map(actorMapper::entityToDTO);
        actorDTO.get().setMovieDTOList(optionalActor.get().getMovieList().stream().map(movieMapper::entityToDTO).toList());
        return actorDTO;
    }

    @Override
    public void deleteActor(Integer id) {
        Actor actor=actorRepository.findById(id).orElse(null);
        if (actor!=null){
            List<Movie> movies=actor.getMovieList();
            for (Movie movie:movies){
                movie.getActorList().remove(actor);
            }
        }
        actorRepository.deleteById(id);
    }

    @Override
    public List<ActorDTO> getAllActors() {
        return actorRepository.findAll().stream().map(actorMapper::entityToDTO).toList();
    }

    @Override
    public List<ActorDTO> findAllByIds(List<Integer> actorIds) {
        List<Actor> actors=actorRepository.findAllById(actorIds);
        return actors.stream().map(actorMapper::entityToDTO).toList();
    }
}
