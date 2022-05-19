package ru.pervukhin.gamelist.domain.mapper;

import org.json.JSONObject;

import ru.pervukhin.gamelist.db.author.AuthorTable;
import ru.pervukhin.gamelist.domain.Author;

public class AuthorMapper {

    public Author jsonToAuthor(JSONObject json){
        Author author = null;

        try {
            author = new Author(
                    json.getInt("id"),
                    json.getString("name")

            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return author;
    }

    public AuthorTable AuthorToTable(Author author){
        AuthorTable authorTable = new AuthorTable(
                author.getId(),
                author.getName()
        );
        return authorTable;
    }

    public Author tableToAuthor(AuthorTable authorTable){
        Author author = new Author(
                authorTable.getId(),
                authorTable.getName()
        );
        return author;
    }
}
