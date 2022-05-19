package ru.pervukhin.gamelist.db.genre;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GenreDao {

    @Query("SELECT * FROM genretable ")
    List<GenreTable> getAll();

    @Query("SELECT * FROM genretable WHERE id = :id")
    GenreTable getById(int id);

    @Query("DELETE FROM genretable")
    void deleteAll();

    @Insert
    void insert(GenreTable genreTable);

    @Update
    void update(GenreTable genreTable);

    @Delete
    void delete(GenreTable genreTable);
}
