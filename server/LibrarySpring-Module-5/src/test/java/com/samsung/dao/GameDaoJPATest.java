package com.samsung.dao;

import com.samsung.domain.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Класс gameDaoJPA")
@DataJpaTest
class GameDaoJPATest {

    private static final int EXISTING_ID1 = 1;
    private static final int EXISTING_AUTHOR_COUNT = 4;
    public static final int EXISTING_ID2 = 2;
    public static final int EXISTING_ID3 = 3;
    public static final int EXISTING_ID4 = 4;
    public static final String EXISTING_NAME1 = "Ночной дозор";
    public static final String EXISTING_NAME2 = "Лабиринты отражения";
    public static final String EXISTING_NAME3 = "Горе от ума";
    public static final String EXISTING_NAME4 = "Неукротимая планета";
    public static final int AUTHOR_ID1 = 1;
    public static final int GENRE_ID1 = 1;
    public static final int AUTHOR_ID2 = 2;
    public static final int GENRE_ID2 = 2;
    public static final int AUTHOR_ID3 = 3;
    public static final int GENRE_ID3 = 3;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private AuthorDao authorDao;

    @PersistenceContext
    private EntityManager entityManager;

    @DisplayName("должен добавлять книгу")
    @Test
    void shouldInsertgame() {

        Game expectedgame = Game.builder()
                .id(5)
                .name("qwer")
                .author(authorDao.getById(AUTHOR_ID1))
                .genre(genreDao.getById(GENRE_ID1))
                .build();

        gameDao.save(expectedgame);
        Game actualgame = gameDao.getById(5);

        assertThat(actualgame).isEqualTo(expectedgame);
    }

    @DisplayName("должен обновлять книгу")
    @Test
    void shouldUpdateGame() {

        Game expectedgame1 = Game.builder()
                .id(EXISTING_ID1)
                .name(EXISTING_NAME2)
                .build();

        gameDao.save(expectedgame1);
        Game actualgame = gameDao.getById(EXISTING_ID1);

        assertThat(actualgame.getName()).isEqualTo(expectedgame1.getName());
    }

    @DisplayName("должен возвращать все книги")
    @Test
    void shouldGetAllGames() {

        assertThat(gameDao.findAll().size()).isEqualTo(EXISTING_AUTHOR_COUNT);

    }

    @DisplayName("должен возвращать книгу по id")
    @Test
    void shouldGetGameById() {

        Game expectedgame1 = Game.builder()
                .id(EXISTING_ID1)
                .name(EXISTING_NAME1)
                .author(authorDao.getById(AUTHOR_ID1))
                .genre(genreDao.getById(AUTHOR_ID1))
                .build();

        Game actualgame = gameDao.getById(EXISTING_ID1);

        assertThat(actualgame.getName()).isEqualTo(expectedgame1.getName());
        assertThat(actualgame.getId()).isEqualTo(expectedgame1.getId());
        assertThat(actualgame.getAuthor()).isEqualTo(expectedgame1.getAuthor());
        assertThat(actualgame.getGenre()).isEqualTo(expectedgame1.getGenre());
    }

    @DisplayName("должен возвращать книгу по имени")
    @Test
    void shouldGetAuthorByName() {

        Game expectedgame1 = Game.builder()
                .id(EXISTING_ID1)
                .name(EXISTING_NAME1)
                .author(authorDao.getById(AUTHOR_ID1))
                .genre(genreDao.getById(AUTHOR_ID1))
                .build();

        Game actualgame = gameDao.findByName(EXISTING_NAME1);

        assertThat(actualgame.getName()).isEqualTo(expectedgame1.getName());
        assertThat(actualgame.getId()).isEqualTo(expectedgame1.getId());
        assertThat(actualgame.getAuthor()).isEqualTo(expectedgame1.getAuthor());
        assertThat(actualgame.getGenre()).isEqualTo(expectedgame1.getGenre());
    }

    @DisplayName("должен удалять книгу по id")
    @Test
    void shouldDeletegameById() {


        gameDao.deleteById(EXISTING_ID1);

        assertThatThrownBy(() -> gameDao.getById(EXISTING_ID1)).isInstanceOf(JpaObjectRetrievalFailureException.class);
    }

}
