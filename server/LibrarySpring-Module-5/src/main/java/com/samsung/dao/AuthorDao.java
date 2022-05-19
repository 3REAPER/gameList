package com.samsung.dao;

import com.samsung.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface
AuthorDao extends JpaRepository<Author, Integer> {

    // SELECT * FROM author WHERE name = ?
    Author findByName(String name);
}
