package ru.pervukhin.gamelist.rest;

import ru.pervukhin.gamelist.domain.Game;

public interface GameAPI {

    public void fillGame();

    public void setRating(int id, int rating);

    public void createGame(Game game);

    public void updateGame(Game game);

    public void deleteGame(int id);

    public void fillGenre();

    public void fillAuthor();
}
