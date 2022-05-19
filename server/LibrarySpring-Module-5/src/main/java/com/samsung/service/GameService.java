package com.samsung.service;

import com.samsung.domain.Game;

import java.util.List;

public interface GameService {

    Game insert(String nameGame,
                String description,
                double rating,
                int countRating,
                String nameGenre,
                String nameAuthor);

    Game update(int id,
                String nameGame,
                String description,
                String nameGenre,
                String nameAuthor);

    Game updateWithRating(int id,
                String nameGame,
                String description,
                double rating,
                int countRating,
                String nameGenre,
                String nameAuthor);

    List<Game> getAll();

    Game getById(int id);

    Game getByName(String name);

    void deleteById(int id);
}
