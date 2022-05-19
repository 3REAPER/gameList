package ru.pervukhin.gamelist.db.game;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ru.pervukhin.gamelist.db.author.AuthorTable;
import ru.pervukhin.gamelist.db.genre.GenreTable;
import ru.pervukhin.gamelist.domain.Author;
import ru.pervukhin.gamelist.domain.Genre;

@Entity
public class GameTable {
    @PrimaryKey
    private int id;
    private String name;
    private String description;
    private int rating;
    private int idAuthor;
    private int idGenre;

    public GameTable(int id, String name, String description, int rating, int idAuthor, int idGenre) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.idAuthor = idAuthor;
        this.idGenre = idGenre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public int getIdGenre() {
        return idGenre;
    }
}
