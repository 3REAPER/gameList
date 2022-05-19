package ru.pervukhin.gamelist.domain.mapper;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.List;

import ru.pervukhin.gamelist.App;
import ru.pervukhin.gamelist.db.AppDataBase;
import ru.pervukhin.gamelist.db.author.AuthorTable;
import ru.pervukhin.gamelist.db.game.GameTable;
import ru.pervukhin.gamelist.db.genre.GenreTable;
import ru.pervukhin.gamelist.domain.Author;
import ru.pervukhin.gamelist.domain.Game;
import ru.pervukhin.gamelist.domain.Genre;

public class GameMapper {
    private AppDataBase dataBase;

    public GameMapper() {
        dataBase = App.getInstance().getDatabase();
    }

    public Game jsonToGame(JSONObject json, Author author, Genre genre){
        Game game = null;

        try {
            game = new Game(
                    json.getInt("id"),
                    json.getString("name"),
                    json.getString("description"),
                    (int) Math.round(json.getDouble("rating")),
                    author,
                    genre
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return game;
    }

    public GameTable gameToTable(Game game){
        GameTable gameTable = new GameTable(
                game.getId(),
                game.getName(),
                game.getDescription(),
                game.getRating(),
                game.getAuthor().getId(),
                game.getGenre().getId()
        );
        return gameTable;
    }

    public Game tableToGame(GameTable gameTable){


        Game game = new Game(
                gameTable.getId(),
                gameTable.getName(),
                gameTable.getDescription(),
                gameTable.getRating(),
                new AsyncAuthor().doInBackground(gameTable.getIdAuthor()),
                new AsyncGenre().doInBackground(gameTable.getIdGenre())
        );
        return game;
    }

    private class AsyncAuthor extends AsyncTask<Integer,Author,Author>{

        @Override
        protected Author doInBackground(Integer... integers) {
            AuthorTable authorTable = dataBase.authorDao().getById(integers[0]);
            Author author  = new AuthorMapper().tableToAuthor(authorTable);
            return new Author(author.getId(),author.getName());
        }
    }

    private class AsyncGenre extends AsyncTask<Integer,Genre, Genre>{

        @Override
        protected Genre doInBackground(Integer... integers) {
            GenreTable genreTable = dataBase.genreDao().getById(integers[0]);
            Genre genre = new GenreMapper().tableToGenre(genreTable);
            return new Genre(genre.getId(),genre.getName());
        }
    }
}
