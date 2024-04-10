package movie.mapper;

import movie.controller.model.ActorDTO;
import movie.entity.Actor;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ActorMapper {
    ActorDTO entityToDTO(Actor actor);
    Actor DTOToEntity(ActorDTO actorSaveRequestDTO);
}
