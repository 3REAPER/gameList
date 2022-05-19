package com.samsung.dao;

import com.samsung.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс GenreDaoJPA")
@DataJpaTest
class GenreDaoJPATest {

    public static final int EXISTING_ID1 = 1;
    public static final int EXISTING_ID2 = 2;
    public static final int EXISTING_ID3 = 3;
    public static final String EXISTING_NAME1 = "Фэнтези";
    public static final String EXISTING_NAME2 = "Комедия";
    public static final String EXISTING_NAME3 = "Фантастика";
    public static final int EXISTING_GENRE_COUNT = 3;

    @Autowired
    private GenreDao dao;

    @PersistenceContext
    private EntityManager entityManager;

    @DisplayName("должен добавлять жанр")
    @Test
    void shouldInsertGenre() {

        Genre expectedGenre = Genre.builder()
                .id(4)
                .name("Роман")
                .build();

        dao.save(expectedGenre);
        Genre actualGenre = dao.getById(4);

        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @DisplayName("должен обновлять жанр")
    @Test
    void shouldUpdateGenre() {

        Genre expectedGenre = Genre.builder()
                .id(EXISTING_ID1)
                .name("Роман")
                .build();

        dao.save(expectedGenre);
        Genre actualGenre = dao.getById(EXISTING_ID1);

        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @DisplayName("должен возвращать все жанры")
    @Test
    void shouldGetAllGenre() {

        Genre expectedGenre1 = Genre.builder()
                .id(EXISTING_ID1)
                .name(EXISTING_NAME1)
                .build();
        Genre expectedGenre2 = Genre.builder()
                .id(EXISTING_ID2)
                .name(EXISTING_NAME2)
                .build();
        Genre expectedGenre3 = Genre.builder()
                .id(EXISTING_ID3)
                .name(EXISTING_NAME3)
                .build();

        assertThat(dao.findAll().size()).isEqualTo(EXISTING_GENRE_COUNT);
        assertThat(dao.findAll())
                .containsExactlyInAnyOrder(expectedGenre1, expectedGenre2, expectedGenre3);
    }

    @DisplayName("должен возвращать жанр по id")
    @Test
    void shouldGetGenreById() {

        Genre expectedGenre = Genre.builder()
                .id(EXISTING_ID1)
                .name(EXISTING_NAME1)
                .build();

        Genre actualGenre = dao.getById(EXISTING_ID1);

        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @DisplayName("должен возвращать жанр по имени")
    @Test
    void shouldGetGenreByName() {

        Genre expectedGenre = Genre.builder()
                .id(EXISTING_ID1)
                .name(EXISTING_NAME1)
                .build();

        Genre actualGenre = dao.findByName(EXISTING_NAME1);

        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @DisplayName("должен удалять жанр по id")
    @Test
    void shouldDeleteGenreById() {

        int sizeBefore = dao.findAll().size();

        dao.deleteById(EXISTING_ID1);
        entityManager.flush();

        int sizeAfter = dao.findAll().size();
        assertThat(sizeBefore - sizeAfter).isEqualTo(1);
    }
}
