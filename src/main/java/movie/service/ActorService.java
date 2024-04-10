package movie.service;

import movie.controller.model.ActorDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ActorService {
    List<ActorDTO> findByKeyword(String keyword);

    void save(ActorDTO actorDTO,List<Integer> movieIds);

    Optional<ActorDTO> findById(Integer id);

    void deleteActor(Integer id);

    List<ActorDTO> getAllActors();

    List<ActorDTO> findAllByIds(List<Integer> actorIds);

}
