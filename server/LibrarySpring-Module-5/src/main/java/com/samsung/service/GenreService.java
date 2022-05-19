package com.samsung.service;


import com.samsung.domain.Genre;

import java.util.List;

public interface GenreService {

    Genre insert(Genre genre);

    Genre update(int id, String name);

    List<Genre> getAll();

    Genre getById(int id);

    Genre getByName(String name);

    void deleteById(int id);
}
