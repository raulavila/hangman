package com.raulavila.hangman.factory;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raulavila.hangman.dao.WordDao;
import com.raulavila.hangman.model.Game;

@Service
public class GameFactory {

	@Autowired
	WordDao wordDao;
	
	public Game createNewGame() {
		UUID id = UUID.randomUUID();
		String word = wordDao.getRandomWord();
		
		Game game = new Game(id.toString(), word);
		
		return game;
	}
	
	public Game getGameFromJson(String json) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, Game.class);
	}
	
}
