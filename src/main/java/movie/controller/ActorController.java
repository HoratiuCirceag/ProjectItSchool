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
@RequestMapping("/actors")
public class ActorController {
    public final ActorService actorService;
    public final MovieService movieService;

    @GetMapping("/all")
    public String listActors(Model model, @RequestParam(required = false) String keyword){
        List<ActorDTO> actors=actorService.findByKeyword(keyword);
        model.addAttribute("actors", Objects.requireNonNullElseGet(actors, ArrayList::new));
        return "actors";
    }

    @GetMapping(value = "/add")
    public String showAddActorForm(Model model) {
        model.addAttribute("actor", new ActorDTO());
        model.addAttribute("movies", movieService.getAllMovies());
        return "actors-add";
    }
    @PostMapping("/add")
    public String processAddActorForm(@ModelAttribute("actor") ActorDTO actorDTO,@RequestParam(required = false) List<Integer> movieIds){
        actorService.save(actorDTO,movieIds);
        return "redirect:/actors/all";
    }

    @GetMapping(value = "/edit/{id}")
    public String showEditActorForm(@PathVariable Integer id,Model model){
        model.addAttribute("actorToBeEdited", actorService.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid actor id:" + id)));
        model.addAttribute("movies",movieService.getAllMovies());
        return "actors-edit";
    }

    @PostMapping("/edit/{id}")
    public String processEditActorForm(@PathVariable Integer id,@ModelAttribute("actor") ActorDTO actorDTO,@RequestParam(required = false) List<Integer> movieIds){
        actorDTO.setId(id);
        actorService.save(actorDTO,movieIds);
        return "redirect:/actors/all";
    }

    @GetMapping("/view/{id}")
    public String showViewActorDetails(@PathVariable Integer id,Model model){
        ActorDTO actorDTO=actorService.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid actor id:" +id));
        model.addAttribute("actor",actorDTO);
        return "actors-view";
    }

    @GetMapping("/delete/{id}")
    public String deleteActor(@PathVariable Integer id){
        actorService.deleteActor(id);
        return "redirect:/actors/all";
    }
}
