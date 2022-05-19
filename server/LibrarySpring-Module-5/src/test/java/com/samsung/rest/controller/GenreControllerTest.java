package com.samsung.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsung.domain.Genre;
import com.samsung.rest.dto.GenreDto;
import com.samsung.service.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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


@WebMvcTest(GenreController.class)
class GenreControllerTest {

    public static final String GENRE_NAME_1 = "genreName1";
    public static final int GENRE_ID1 = 1;
    public static final Genre GENRE1 = new Genre(GENRE_ID1, GENRE_NAME_1);
    public static final String GENRE_NAME_2 = "genreName2";
    public static final int GENRE_ID2 = 2;
    public static final Genre GENRE2 = new Genre(GENRE_ID2, GENRE_NAME_2);

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreService genreService;

    @Test
    void shouldCreateNewGenre() throws Exception {

        given(genreService.insert(GENRE1)).willReturn(GENRE1);

        GenreDto expectedResult = GenreDto.toDto(GENRE1);

        mvc.perform(post("/genre").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(expectedResult)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldGetAllGenres() throws Exception {

        List<Genre> genres = new ArrayList<>();
        genres.add(GENRE1);
        genres.add(GENRE2);
        given(genreService.getAll()).willReturn(genres);

        List<GenreDto> expectedResult = genres.stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());

        mvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldGetGenreById() throws Exception {

        given(genreService.getById(GENRE_ID1)).willReturn(GENRE1);

        GenreDto expectedResult = GenreDto.toDto(GENRE1);

        mvc.perform(get("/genre/" + GENRE_ID1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldGetGenreByName() throws Exception {

        given(genreService.getByName(GENRE_NAME_1)).willReturn(GENRE1);

        GenreDto expectedResult = GenreDto.toDto(GENRE1);

        mvc.perform(get("/genre/name")
                        .param("name", String.valueOf(GENRE_NAME_1)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldUpdateNameById() throws Exception {

        given(genreService.update(GENRE_ID1, GENRE_NAME_1)).willReturn(GENRE1);

        GenreDto expectedResult = GenreDto.toDto(GENRE1);

        mvc.perform(post("/genre/1/name")
                        .param("name", GENRE_NAME_1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldDeleteGenreById() throws Exception {

        mvc.perform(delete("/genre/" + GENRE_ID1))
                .andExpect(status().isOk());

        verify(genreService, times(1)).deleteById(1);
    }
}