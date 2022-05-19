package com.samsung.dao;

import com.samsung.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreDao extends JpaRepository<Genre, Integer> {

    // SELECT * FROM genre WHERE name = ?
    Genre findByName(String name);
}
