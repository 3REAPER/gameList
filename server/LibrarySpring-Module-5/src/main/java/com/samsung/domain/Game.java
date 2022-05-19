package com.samsung.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincrement
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private double rating;

    @Column(name = "count_rating")
    private int countRating;

    @ManyToOne(targetEntity = Author.class, fetch = FetchType.LAZY) // Вид связи многое к одному (у одного автора
    @JoinColumn(name = "author_id")                                 // много книг), выгружаем автора, когда необходимо
    private Author author;

    @ManyToOne(targetEntity = Genre.class, fetch = FetchType.LAZY) // Вид связи многое к одному (у одного жанра
    @JoinColumn(name = "genre_id")                                 // много книг), выгружаем жанр, когда необходимо
    private Genre genre;
}
