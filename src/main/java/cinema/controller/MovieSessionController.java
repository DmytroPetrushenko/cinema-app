package cinema.controller;

import cinema.dto.request.MovieSessionRequestDto;
import cinema.dto.response.MovieSessionResponseDto;
import cinema.model.MovieSession;
import cinema.service.MovieSessionService;
import cinema.service.mapper.MovieSessionMapper;
import cinema.util.DateTimePatternUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movie-sessions")
public class MovieSessionController {
    private static final String DATE_PATTERN = DateTimePatternUtil.DATE_PATTERN;
    private final MovieSessionService movieSessionService;
    private final MovieSessionMapper movieSessionMapper;

    public MovieSessionController(MovieSessionService movieSessionService,
                                  MovieSessionMapper movieSessionMapper) {
        this.movieSessionService = movieSessionService;
        this.movieSessionMapper = movieSessionMapper;
    }

    @PostMapping
    public MovieSessionResponseDto add(@RequestBody @Valid MovieSessionRequestDto requestDto) {
        MovieSession movieSession = movieSessionMapper.mapToModel(requestDto);
        movieSessionService.add(movieSession);
        return movieSessionMapper.mapToDto(movieSession);
    }

    @GetMapping("/available")
    public List<MovieSessionResponseDto> getAll(@RequestParam Long movieId,
                                                @RequestParam
                                                @DateTimeFormat(pattern = DATE_PATTERN)
                                                        LocalDate date) {
        return movieSessionService.findAvailableSessions(movieId, date)
                .stream()
                .map(movieSessionMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public MovieSessionResponseDto update(@PathVariable Long id,
                                          @RequestBody @Valid MovieSessionRequestDto requestDto) {
        MovieSession movieSession = movieSessionMapper.mapToModel(requestDto);
        movieSession.setId(id);
        movieSessionService.update(movieSession);
        return movieSessionMapper.mapToDto(movieSession);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        movieSessionService.delete(id);
    }
}
