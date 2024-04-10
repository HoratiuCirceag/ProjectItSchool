package movie.controller.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MovieDTO {
    private Integer id;

    private String name;

    private Integer duration;

    private Float score;

    private List<ActorDTO> actorDTOList;
}
