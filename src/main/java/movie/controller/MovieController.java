package movie.controller;

import lombok.RequiredArgsConstructor;
import movie.controller.model.ActorDTO;
import movie.controller.model.MovieDTO;
import movie.service.ActorService;
import movie.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    public final MovieService movieService;
    public final ActorService actorService;

    @GetMapping("/all")
    public String listMovies(Model model, @RequestParam(required = false) String keyword){
        List<MovieDTO> movies=movieService.findByKeyword(keyword);
        model.addAttribute("movies", Objects.requireNonNullElseGet(movies, ArrayList::new));
        return "movies";
    }

    @GetMapping(value = "/add")
    public String showAddMovieForm(Model model) {
        model.addAttribute("movie", new MovieDTO());
        model.addAttribute("actors", actorService.getAllActors());
        return "movies-add";
    }
    @PostMapping("/add")
    public String processAddMovieForm(@ModelAttribute("movie") MovieDTO movieDTO, @RequestParam(required = false) List<Integer> actorIds){
        if (actorIds!=null) {
            List<ActorDTO> actors = actorService.findAllByIds(actorIds);
            movieDTO.setActorDTOList(actors);
        }
        movieService.save(movieDTO);
        return "redirect:/movies/all";
    }

    @GetMapping(value = "/edit/{id}")
    public String showEditMovieForm(@PathVariable Integer id,Model model){
        model.addAttribute("movieToBeEdited", movieService.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid movie id:" + id)));
        model.addAttribute("actors", actorService.getAllActors());
        return "movies-edit";
    }

    @PostMapping("/edit/{id}")
    public String processEditMovieForm(@PathVariable Integer id,@ModelAttribute("movie") MovieDTO movieDTO,@RequestParam(required = false) List<Integer> actorIds){
        if (actorIds!=null) {
            List<ActorDTO> actors = actorService.findAllByIds(actorIds);
            movieDTO.setActorDTOList(actors);
        }
        movieDTO.setId(id);
        movieService.save(movieDTO);
        return "redirect:/movies/all";
    }

    @GetMapping("/view/{id}")
    public String showViewMovieDetails(@PathVariable Integer id,Model model){
        MovieDTO movieDTO=movieService.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid movie id:" + id));
        model.addAttribute("movie",movieDTO);
        return "movies-view";
    }



    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Integer id){
        movieService.deleteMovie(id);
        return "redirect:/movies/all";
    }


}
