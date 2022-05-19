package ru.pervukhin.gamelist.domain.mapper;

import org.json.JSONObject;

import ru.pervukhin.gamelist.db.genre.GenreTable;
import ru.pervukhin.gamelist.domain.Genre;

public class GenreMapper {

    public Genre jsonToGenre(JSONObject json){
        Genre genre = null;

        try{
            genre = new Genre(
                    json.getInt("id"),
                    json.getString("name")
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return genre;
    }

    public GenreTable genreToTable(Genre genre){
        GenreTable genreTable = new GenreTable(
                genre.getId(),
                genre.getName()
        );
        return genreTable;
    }

    public Genre tableToGenre(GenreTable genreTable){
        Genre genre = new Genre(
                genreTable.getId(),
                genreTable.getName()
        );
        return genre;
    }
}
