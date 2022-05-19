package ru.pervukhin.gamelist.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.pervukhin.gamelist.db.author.AuthorDao;
import ru.pervukhin.gamelist.db.author.AuthorTable;
import ru.pervukhin.gamelist.db.game.GameDao;
import ru.pervukhin.gamelist.db.game.GameTable;
import ru.pervukhin.gamelist.db.genre.GenreDao;
import ru.pervukhin.gamelist.db.genre.GenreTable;
import ru.pervukhin.gamelist.db.rating.RatingDao;
import ru.pervukhin.gamelist.db.rating.RatingTable;


@Database(entities = {GameTable.class, AuthorTable.class, GenreTable.class, RatingTable.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract GameDao gameDao();

    public abstract AuthorDao authorDao();

    public abstract GenreDao genreDao();

    public abstract RatingDao ratingDao();
}
