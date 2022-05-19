package ru.pervukhin.gamelist.db.rating;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RatingTable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int gameId;
    private String gameName;
    private int sendId; // 0 - rating don't send, 1 - waiting answer server, 2 - rating send
    private int rating;

    public RatingTable(int gameId, String gameName, int sendId, int rating) {
        this.gameId = gameId;
        this.sendId = sendId;
        this.rating = rating;
        this.gameName = gameName;
    }



    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getGameId() {
        return gameId;
    }

    public int getSendId() {
        return sendId;
    }

    public int getRating() {
        return rating;
    }

    public String getGameName() {
        return gameName;
    }
}
