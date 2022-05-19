package com.samsung.service;

import com.samsung.dao.AuthorDao;
import com.samsung.domain.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    @Transactional
    public Author insert(Author author) {
        return authorDao.save(author);
    }


    @Override
    @Transactional
    public Author update(int id, String newNameAuthor) {

        Author author = Author.builder()
                .id(id)
                .name(newNameAuthor)
                .build();

        return authorDao.save(author);
    }

    @Override
    public List<Author> getAll() {

        return authorDao.findAll();
    }

    @Override
    public Author getById(int id) {

        return authorDao.getById(id);
    }

    @Override
    public Author getByName(String nameAuthor) {
        return authorDao.findByName(nameAuthor);
    }

    @Override
    @Transactional
    public void deleteById(int id) {

        authorDao.deleteById(id);
    }
}
