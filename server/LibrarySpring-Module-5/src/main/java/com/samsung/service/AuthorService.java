package com.samsung.service;

import com.samsung.domain.Author;

import java.util.List;

public interface AuthorService {

    Author insert(Author author);

    Author update(int id, String newNameAuthor);

    List<Author> getAll();

    Author getById(int id);

    Author getByName(String nameAuthor);

    void deleteById(int id);
}
