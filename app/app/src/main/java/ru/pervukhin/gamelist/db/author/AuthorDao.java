package ru.pervukhin.gamelist.db.author;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.pervukhin.gamelist.db.author.AuthorTable;

@Dao
public interface AuthorDao {

    @Query("SELECT * FROM authortable")
    List<AuthorTable> getAll();

    @Query("SELECT * FROM authortable WHERE id = :id")
    AuthorTable getById(int id);

    @Query("DELETE FROM authortable")
    void deleteAll();

    @Insert
    void insert(AuthorTable authorTable);

    @Update
    void update(AuthorTable authorTable);

    @Delete
    void delete(AuthorTable authorTable);
}
