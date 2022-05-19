package ru.pervukhin.gamelist.db;

import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ru.pervukhin.gamelist.App;
import ru.pervukhin.gamelist.db.author.AuthorTable;
import ru.pervukhin.gamelist.db.game.GameTable;
import ru.pervukhin.gamelist.db.genre.GenreTable;
import ru.pervukhin.gamelist.db.rating.RatingTable;
import ru.pervukhin.gamelist.db.rating.SendId;
import ru.pervukhin.gamelist.domain.Author;
import ru.pervukhin.gamelist.domain.Game;
import ru.pervukhin.gamelist.domain.Genre;
import ru.pervukhin.gamelist.domain.mapper.AuthorMapper;
import ru.pervukhin.gamelist.domain.mapper.GameMapper;
import ru.pervukhin.gamelist.domain.mapper.GenreMapper;
import ru.pervukhin.gamelist.rest.GameAPI;
import ru.pervukhin.gamelist.ui.fragments.ListFragment;

public class DataManager {
    private AppDataBase db;

    public DataManager() {
        db = App.getInstance().getDatabase();
    }

    public List<Author> getAllAuthor() {
        List<AuthorTable>  gameTableList = db.authorDao().getAll();
        List<Author> authorList = new ArrayList<>();
        for(int i = 0; i < gameTableList.size();i++){
            authorList.add(new AuthorMapper().tableToAuthor(gameTableList.get(i)));
        }
        return authorList;
    }

    public  void insertAuthor(Author author){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AuthorTable game = db.authorDao().getById(author.getId());
                if (game == null) {
                    db.authorDao().insert(new AuthorMapper().AuthorToTable(author));
                }
            }
        }).start();
    }

    public List<Genre> getAllGenre() {
        List<GenreTable>  genreTableList = db.genreDao().getAll();
        List<Genre> genreList = new ArrayList<>();
        for(int i = 0; i < genreTableList.size();i++){
            genreList.add(new GenreMapper().tableToGenre(genreTableList.get(i)));
        }
        return genreList;
    }

    public  void insertGenre(Genre genre){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (db.genreDao().getById(genre.getId()) == null) {
                    db.genreDao().insert(new GenreMapper().genreToTable(genre));
                }
            }
        }).start();
    }
    
    public void insertGame(Game game, @Nullable WeakReference<ListFragment> fragment){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    db.gameDao().insert(new GameMapper().gameToTable(game));
                    if (db.authorDao().getById(game.getAuthor().getId()) == null) {
                        db.authorDao().insert(new AuthorMapper().AuthorToTable(game.getAuthor()));
                    }
                    if (db.genreDao().getById(game.getGenre().getId()) == null) {
                        db.genreDao().insert(new GenreMapper().genreToTable(game.getGenre()));
                    }
                    if (db.ratingDao().findByIdGame(game.getId()) == null ){
                        db.ratingDao().insert(new RatingTable(game.getId(), game.getName(), SendId.DONT_SEND,0));
                    }
                    Log.d("dataBase","data was create");
                    if (fragment != null) {
                        fragment.get().onGetJson(game);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("dataBase","data creating error");
                }
            }
        }).start();
    }
    
    public void deleteAllGame(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    db.gameDao().deleteAll();
                    Log.d("dataBase","data was delete");
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("dataBase","data deleting error");
                }
            }
        }).start();

    }


    public List<Game> getAllGame() {
        List<GameTable>  gameTableList = db.gameDao().getAll();
        List<Game> gameList = new ArrayList<>();
        for(int i = 0; i < gameTableList.size();i++){
            gameList.add(new GameMapper().tableToGame(gameTableList.get(i)));
        }
        return gameList;
    }

    public Game getGameById(int id) {
        return new GameMapper().tableToGame(db.gameDao().findById(id));
    }

    public void insertRating(RatingTable ratingTable){
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.ratingDao().insert(ratingTable);
            }
        }).start();
    }

    public void updateRating(RatingTable ratingTable){
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.ratingDao().update(ratingTable);
            }
        }).start();
    }

    public RatingTable findRatingByGameId(int gameID){
        return db.ratingDao().findByIdGame(gameID);
    }

    public List<RatingTable> getRatingWithNoSend() {
        return db.ratingDao().getRatingWithNoSend();

    }

    public List<RatingTable> getRatingWithSend() {
        return db.ratingDao().getRatingWithSend();

    }

    public List<Game> getGameWithRatingNoSend() {
        List<RatingTable> ratingTables = getRatingWithNoSend();
        List<Game> games = getAllGame();
        List<Game> result = new ArrayList<>();
        for (RatingTable ratingTable : ratingTables ) {
            for (Game game : games) {
                if (game.getId() == ratingTable.getGameId()){
                    result.add(game);
                }
            }

        }
        return result;
    }

    public List<Game> getGameWithRatingSend() {
        List<RatingTable> ratingTables = getRatingWithSend();
        List<Game> games = getAllGame();
        List<Game> result = new ArrayList<>();
        for (RatingTable ratingTable : ratingTables ) {
            for (Game game : games) {
                if (game.getId() == ratingTable.getGameId()){
                    result.add(game);
                }
            }

        }
        return result;
    }
}