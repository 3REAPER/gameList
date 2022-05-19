package ru.pervukhin.gamelist.db.game;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GameDao {

    @Query("SELECT * FROM gameTable WHERE id = :id")
    GameTable findById(int id);

    @Query("SELECT * FROM gameTable")
    List<GameTable> getAll();

    @Insert
    void insert(GameTable gameTable);

    @Update
    void update(GameTable gameTable);

    @Query("DELETE FROM gameTable")
    void deleteAll();

    @Delete
    void delete(GameTable gameTable);
}
