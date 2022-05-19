package com.samsung.service;

import com.samsung.dao.AuthorDao;
import com.samsung.dao.GameDao;
import com.samsung.dao.GenreDao;
import com.samsung.domain.Author;
import com.samsung.domain.Game;
import com.samsung.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GenreDao genreDao;
    private final GameDao gameDao;
    private final AuthorDao authorDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    @Transactional
    public Game insert(String nameGame,
                       String description,
                       double rating,
                       int countRating,
                       String nameGenre,
                       String nameAuthor) {

        //на скок адекватно делать так? не придумал иначе
        Author author = authorDao.findByName(nameAuthor);
        if (author == null) {
            author = Author.builder()
                    .name(nameAuthor)
                    .build();
        }

        Genre genre = genreDao.findByName(nameGenre);
        if (genre == null) {
            genre = Genre.builder()
                    .name(nameGenre)
                    .build();
        }

        Game game = Game.builder()
                .name(nameGame)
                .description(description)
                .rating(rating)
                .countRating(countRating)
                .author(author)
                .genre(genre)
                .build();

        return gameDao.save(game);
    }


    @Override
    @Transactional
    public Game update(int id,
                       String nameGame,
                       String description,
                       String nameAuthor,
                       String nameGenre) {
        Game game = gameDao.getById(id);
        Game gameUpdate = Game.builder()
                .id(id)
                .name(nameGame)
                .description(description)
                .rating(game.getRating())
                .author(authorService.getByName(nameAuthor))
                .genre(genreService.getByName(nameGenre))
                .build();

        return gameDao.save(gameUpdate);
    }

    @Override
    public Game updateWithRating(int id, String nameGame, String description, double rating, int countRating, String nameGenre, String nameAuthor) {
        Game game = Game.builder()
                .id(id)
                .name(nameGame)
                .description(description)
                .rating(rating)
                .countRating(countRating)
                .author(authorService.getByName(nameAuthor))
                .genre(genreService.getByName(nameGenre))
                .build();

        return gameDao.save(game);
    }

    @Override
    public List<Game> getAll() {

        return gameDao.findAll();
    }

    @Override
    public Game getById(int id) {

        return gameDao.getById(id);
    }

    @Override
    public Game getByName(String name) {

        return gameDao.findByName(name);
    }

    @Transactional
    @Override
    public void deleteById(int id) {

        gameDao.deleteById(id);
    }
}
