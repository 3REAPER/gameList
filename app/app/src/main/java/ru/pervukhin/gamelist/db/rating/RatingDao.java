package ru.pervukhin.gamelist.db.rating;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RatingDao {

    @Query("SELECT * FROM ratingtable WHERE gameId = :id")
    RatingTable findByIdGame(int id);

    @Query("SELECT * FROM ratingtable WHERE sendId = " +SendId.DONT_SEND)
    List<RatingTable> getRatingWithNoSend();

    @Query("SELECT * FROM ratingtable WHERE sendId = " +SendId.SEND)
    List<RatingTable> getRatingWithSend();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RatingTable ratingTable);

    @Delete
    void delete(RatingTable ratingTable);

    @Update
    void update(RatingTable ratingTable);
}
