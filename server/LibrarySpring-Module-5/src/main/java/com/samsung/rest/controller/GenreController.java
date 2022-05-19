package com.samsung.rest.controller;

import com.samsung.domain.Genre;
import com.samsung.rest.dto.GenreDto;
import com.samsung.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/genre")
    public GenreDto createNewGenre(@RequestBody GenreDto genreDto) {

        Genre genre = genreService.insert(GenreDto.toDomainObject(genreDto));
        return GenreDto.toDto(genre);
    }

    @GetMapping("/genre")
    public List<GenreDto> getAllGenres() {

        return genreService
                .getAll()
                .stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/genre/{id}")
    public GenreDto getGenreById(@PathVariable int id) {

        return GenreDto.toDto(genreService.getById(id));
    }

    @GetMapping("/genre/name")
    public GenreDto getGenreByName(@RequestParam String name) {

        return GenreDto.toDto(genreService.getByName(name));
    }

    @PostMapping("/genre/{id}/name")
    public GenreDto updateNameById(
            @PathVariable int id,
            @RequestParam String name
    ) {

        return GenreDto.toDto(
                genreService.update(id, name)
        );
    }

    @DeleteMapping("/genre/{id}")
    public void deleteGenreById(@PathVariable int id) {

        genreService.deleteById(id);
    }

}
