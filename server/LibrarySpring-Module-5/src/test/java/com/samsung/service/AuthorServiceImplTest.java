package com.samsung.service;

import com.samsung.dao.AuthorDao;
import com.samsung.domain.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Класс AuthorServiceImpl")
@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    public static final int EXISTING_ID1 = 1;
    public static final String EXISTING_NAME1 = "Сергей Лукьяненко";
    public static final int EXISTING_ID2 = 2;
    public static final int EXISTING_ID3 = 3;
    public static final String EXISTING_NAME2 = "Александр Сергеевич Грибоедов";
    public static final String EXISTING_NAME3 = "Гарри Гаррисон";

    private AuthorService authorService;

    @Mock
    private AuthorDao authorDao;

    private List<Author> authors;

    @BeforeEach
    void init() {
        authors = new ArrayList<>();

        Author expectedAuthor1 = Author.builder()
                .id(EXISTING_ID1)
                .name(EXISTING_NAME1)
                .build();
        Author expectedAuthor2 = Author.builder()
                .id(EXISTING_ID2)
                .name(EXISTING_NAME2)
                .build();
        Author expectedAuthor3 = Author.builder()
                .id(EXISTING_ID3)
                .name(EXISTING_NAME3)
                .build();

        authors.add(expectedAuthor1);
        authors.add(expectedAuthor2);
        authors.add(expectedAuthor3);

        authorService = new AuthorServiceImpl(authorDao);
    }

    @DisplayName("должен получать всех авторов")
    @Test
    void shouldGetAllAuthors() {
        when(authorDao.findAll()).thenReturn(authors);

        List<Author> expectedAuthors = authorService.getAll();

        assertThat(expectedAuthors).isEqualTo(authors);
    }

    @DisplayName("должен получать автора по id")
    @Test
    void shouldGetAuthorById() {
        Author expectedAuthor = Author.builder()
                .id(4)
                .name("Ivan")
                .build();

        when(authorDao.getById(4)).thenReturn(expectedAuthor);

        Author actualAuthor = authorService.getById(4);

        assertThat(expectedAuthor).isEqualTo(actualAuthor);
    }

    @DisplayName("должен получать автора по имени")
    @Test
    void shouldGetAuthorByName() {
        Author expectedAuthor = Author.builder()
                .id(4)
                .name("Ivan Ivanov")
                .build();

        when(authorDao.findByName("Ivan Ivanov")).thenReturn(expectedAuthor);

        Author actualAuthor = authorService.getByName("Ivan Ivanov");

        assertThat(expectedAuthor).isEqualTo(actualAuthor);
    }

}