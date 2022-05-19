package com.samsung.rest.dto;

import com.samsung.domain.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {

    private int id;

    private String name;
    
    private String description;

    private double rating;

    private int countRating;

    private AuthorDto authorDto;

    private GenreDto genreDto;
    

    public static Game toDomainObject(GameDto gameDto) {

        return new Game(
                gameDto.getId(),
                gameDto.getName(),
                gameDto.getDescription(),
                gameDto.getRating(),
                gameDto.getCountRating(),
                AuthorDto.toDomainObject(gameDto.getAuthorDto()),
                GenreDto.toDomainObject(gameDto.getGenreDto())
        );
    }

    public static GameDto toDto(Game game) {
        return new GameDto(
                game.getId(),
                game.getName(),
                game.getDescription(),
                game.getRating(),
                game.getCountRating(),
                AuthorDto.toDto(game.getAuthor()),
                GenreDto.toDto(game.getGenre())
        );

    }
}
