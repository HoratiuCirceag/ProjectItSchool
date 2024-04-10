package movie.mapper;

import movie.controller.model.MovieDTO;
import movie.entity.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDTO entityToDTO(Movie movie);
    Movie DTOToEntity(MovieDTO movieSaveRequestDTO);
}
