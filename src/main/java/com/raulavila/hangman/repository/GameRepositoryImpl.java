package com.raulavila.hangman.repository;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.raulavila.hangman.model.Game;
import com.raulavila.hangman.util.BackupUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// The games repository is an in-memory repo. This wouldn't work in distributed environments,
// (there would be one singleton per application context/server)
// but I think it's enough for the exercise.
// The persistence of the games being played is done using a file created inside the webapp (see backupGames method)
@Repository
public class GameRepositoryImpl implements GameRepository {

	@Autowired
	BackupUtils backupUtils;
	
	private static final String BACKUP_GAMES_FILENAME = "backup_games.txt";
	
	ConcurrentMap<String, Game> games = new ConcurrentHashMap<String, Game>();
	
	@Override
	public Game getGame(String gameId) {
		return games.get(gameId);
	}
	
	@Override
	public void addGame(Game game) {
		games.put(game.getGameId(), game);
	}
	
	@Override
	public void removeGame(Game game) {
		games.remove(game.getGameId());
	}
	
	@Override
	public void removeGame(String gameId) {
		games.remove(gameId);
	}

	@Override
	public Collection<Game> getGames() {
		return games.values();
	}
	
	@Override
	public void clearExpiredGames() {
		Date today = new Date();
		
		for(Iterator<Game> it = games.values().iterator(); it.hasNext();) {
			Game game = it.next();
			
			if(today.after(game.getFinishDate()))
				it.remove();
		}
	}
	
	//Try to load the games stored in backup file (in the classpath)
	@PostConstruct
	public void loadGames() {
		Collection<Game> backUpGames = backupUtils.getGamesBackup(BACKUP_GAMES_FILENAME);
		for(Game game: backUpGames)
			games.put(game.getGameId(), game);
	}
	
	//If we shutdown the application server gracefully, this method will be executed, allowing
	//the repository to stores the games being played
	@PreDestroy
	public void backupGames() {
		backupUtils.saveObjectList(BACKUP_GAMES_FILENAME, games.values());
	}
	
}
