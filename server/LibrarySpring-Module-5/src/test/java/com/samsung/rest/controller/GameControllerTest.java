package com.samsung.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsung.domain.Author;
import com.samsung.domain.Game;
import com.samsung.domain.Genre;
import com.samsung.rest.dto.GameDto;
import com.samsung.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
class GameControllerTest {

    public static final int AUTHOR_ID1 = 1;
    public static final String AUTHOR_NAME_1 = "authorName1";
    public static final Author AUTHOR1 = new Author(AUTHOR_ID1, AUTHOR_NAME_1);
    public static final String GENRE_NAME_1 = "genreName1";
    public static final int GENRE_ID1 = 1;
    public static final Genre GENRE1 = new Genre(GENRE_ID1, GENRE_NAME_1);
    public static final String DESCRIPTION = "description1";
    public static final int game_ID1 = 1;
    public static final String game_NAME_1 = "gameName1";
    public static final int RATING = 5;
    public static final int COUNT_RATING = 3;
    public static final Game game1 = new Game(
            game_ID1,
            game_NAME_1,
            DESCRIPTION,
            RATING,
            COUNT_RATING,
            AUTHOR1,
            GENRE1
    );

    public static final int game_ID2 = 2;
    public static final String game_NAME_2 = "gameName2";
    public static final Game game2 = new Game(
            game_ID2,
            game_NAME_2,
            DESCRIPTION,
            RATING,
            COUNT_RATING,
            AUTHOR1,
            GENRE1
    );

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GameService gameService;

    @Test
    void shouldCorrectCreateNewGame() throws Exception {

        given(gameService.insert(game_NAME_1, DESCRIPTION, RATING, COUNT_RATING, GENRE_NAME_1, AUTHOR_NAME_1)).willReturn(game1);

        GameDto expectedResult = GameDto.toDto(game1);

        mvc.perform(post("/game")
                        .param("namegame", game_NAME_1)
                        .param("nameGenre", GENRE_NAME_1)
                        .param("nameAuthor", AUTHOR_NAME_1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldCorrectGetAllGames() throws Exception {

        List<Game> games = new ArrayList<>();
        games.add(game1);
        games.add(game2);

        given(gameService.getAll()).willReturn(games);

        List<GameDto> expectedResult = games.stream()
                .map(GameDto::toDto)
                .collect(Collectors.toList());

        mvc.perform(get("/game"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));

    }

    @Test
    void shouldCorrectUpdateGameById() throws Exception {

        given(gameService.update(game_ID1, game_NAME_1, DESCRIPTION, GENRE_NAME_1, AUTHOR_NAME_1)).willReturn(game1);

        GameDto expectedResult = GameDto.toDto(game1);

        mvc.perform(post("/game/1/")
                        .param("newgameName", game_NAME_1)
                        .param("newGenreName", GENRE_NAME_1)
                        .param("newAuthorName", AUTHOR_NAME_1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));

    }

    @Test
    void shouldCorrectGetGameById() throws Exception {

        given(gameService.getById(game_ID1)).willReturn(game1);

        GameDto expectedResult = GameDto.toDto(game1);

        mvc.perform(get("/game/" + game_ID1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldCorrectGetGameByName() throws Exception {

        given(gameService.getByName(game_NAME_1)).willReturn(game1);

        GameDto expectedResult = GameDto.toDto(game1);

        mvc.perform(get("/game/name")
                        .param("name", String.valueOf(game_NAME_1)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldCorrectDeleteGameById() throws Exception {

        mvc.perform(delete("/game/" + game_ID1))
                .andExpect(status().isOk());

        verify(gameService, times(1)).deleteById(1);
    }
}