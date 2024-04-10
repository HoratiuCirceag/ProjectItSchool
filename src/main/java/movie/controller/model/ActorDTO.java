package movie.controller.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import movie.entity.Movie;

import java.util.List;

@Setter
@Getter
@ToString
public class ActorDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private List<MovieDTO> movieDTOList;
}
