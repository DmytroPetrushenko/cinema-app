package cinema.controller;

import cinema.dto.request.MovieRequestDto;
import cinema.dto.response.MovieResponseDto;
import cinema.model.Movie;
import cinema.service.MovieService;
import cinema.service.mapper.MovieMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    public MovieController(MovieService movieService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

    @PostMapping
    public MovieResponseDto add(@RequestBody @Valid MovieRequestDto requestDto) {
        Movie movie = movieService.add(movieMapper.mapToModel(requestDto));
        return movieMapper.mapToDto(movie);
    }

    @GetMapping
    public List<MovieResponseDto> getAll() {
        return movieService.getAll()
                .stream()
                .map(movieMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
