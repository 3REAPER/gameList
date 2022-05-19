package com.samsung.rest.controller;

import com.samsung.domain.Game;
import com.samsung.rest.dto.GameDto;
import com.samsung.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/game")
    public GameDto createNewGame(
            @RequestParam String nameGame,
            @RequestParam String description,
            @RequestParam int rating,
            @RequestParam String nameGenre,
            @RequestParam String nameAuthor
    ) {
        Game game = gameService.insert(nameGame, description, rating, 1, nameGenre, nameAuthor);
        return GameDto.toDto(game);
    }

    @GetMapping("/game")
    public List<GameDto> getAllGames() {

        return gameService
                .getAll()
                .stream()
                .map(GameDto::toDto)
                .collect(Collectors.toList());
    }


    @PostMapping("/game/{id}/")
    public GameDto updateGameById(
            @PathVariable int id,
            @RequestParam String nameGame,
            @RequestParam String description,
            @RequestParam String nameAuthor,
            @RequestParam String nameGenre
    ) {

        Game game = gameService.update(
                id,
                nameGame,
                description,
                nameAuthor,
                nameGenre
        );

        return GameDto.toDto(game);
    }

    @GetMapping("/game/{id}")
    public GameDto getGameById(@PathVariable int id) {

        return GameDto.toDto(gameService.getById(id));
    }

    @GetMapping("/game/name")
    public GameDto getGameByName(@RequestParam String name) {

        return GameDto.toDto(gameService.getByName(name));
    }

    @PostMapping("/game/{id}/{rating}")
    public GameDto setRating(@PathVariable int id, @PathVariable int rating) {
        Game game = gameService.getById(id);
        if (rating <= 10) {
            int updateCountRating = game.getCountRating() + 1;
            double resultRating = (game.getCountRating() * game.getRating() + rating) / updateCountRating;

            Game gameUpdated = gameService.updateWithRating(
                    game.getId(),
                    game.getName(),
                    game.getDescription(),
                    resultRating,
                    game.getCountRating() + 1,
                    game.getGenre().getName(),
                    game.getAuthor().getName()
            );
            return GameDto.toDto(gameUpdated);
        }
        return GameDto.toDto(game);
    }

    @DeleteMapping("/game/{id}")
    //@PostMapping("/deleteGameById")
    public void deleteGameById(@PathVariable int id) {
        gameService.deleteById(id);
    }

}
