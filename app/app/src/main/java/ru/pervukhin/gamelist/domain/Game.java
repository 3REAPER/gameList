package ru.pervukhin.gamelist.domain;

public class Game {
    private int id;
    private String name;
    private String description;
    private int rating;
    private Author author;
    private Genre genre;

    public Game(int id, String name, String description, int rating, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.author = author;
        this.genre = genre;
    }

    public Game(String name, String description, int rating, Author author, Genre genre) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.author = author;
        this.genre = genre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }
}
