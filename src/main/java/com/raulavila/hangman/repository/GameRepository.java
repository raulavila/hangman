package com.raulavila.hangman.repository;

import java.util.Collection;

import com.raulavila.hangman.model.Game;

public interface GameRepository {

	Game getGame(String gameId);
	
	void addGame(Game game);
	
	void removeGame(Game game);
	
	void removeGame(String gameId);
	
	Collection<Game> getGames();
	
	void clearExpiredGames();
	
}
