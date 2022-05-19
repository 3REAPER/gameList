package ru.pervukhin.gamelist.db.author;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AuthorTable {
    @PrimaryKey
    private int id;
    private String name;

    public AuthorTable(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
