package com.raulavila.hangman.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raulavila.hangman.views.GameUserView;

//Stores one particular game, but this is not what is sent to the client (see GameUserView)
//
//As we need to serialize the game into a JSON and viceversa, it's necessary to make all of its attributes
//mutable, although it's something I don't like much, but Jackson Mapper needs to access them using
//their getters and setters
public class Game {

	private String gameId;
	private String word;
	private Date finishDate;
	
	private int state; 
	private List<Character> lettersPlayed;
	private StringBuilder hits;
	private int failsToLose;
	
	public Game() {}
	
	public Game(String gameId, String word) {
		this.gameId = gameId;
		this.word = word.toUpperCase();
		
		state = 0;
		
		lettersPlayed = new ArrayList<Character>();
		
		hits = new StringBuilder(word.length());
		for(int i=0;i<word.length();i++) {
			hits.append("_");
		}

		this.failsToLose = 5;
		
		finishDate = createFinishDate();
	}
	
	private Date createFinishDate() {
		Calendar c = Calendar.getInstance(); 
		c.setTime(new Date()); 
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}


	public int getState() {
		return state;
	}

	public String getHits() {
		return hits.toString();
	}

	public int getFailsToLose() {
		return failsToLose;
	}

	
	public String getGameId() {
		return gameId;
	}

	public String getWord() {
		return word;
	}
	
	public Date getFinishDate() {
		return finishDate;
	}
	
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setLettersPlayed(List<Character> lettersPlayed) {
		this.lettersPlayed = lettersPlayed;
	}

	public void setHits(String hits) {
		this.hits = new StringBuilder(hits); 
	}

	public void setFailsToLose(int failsToLose) {
		this.failsToLose = failsToLose;
	}

	public List<Character> getLettersPlayed() {
		return lettersPlayed;
	}
	
	
	public void addLetterPlayed(char character) {
		lettersPlayed.add(character);
	}

	public boolean play(char character) {
		state++;
		
		if(Character.isLetter(character)) {
			char upperChar = Character.toUpperCase(character);
			
			addLetterPlayed(upperChar);
			
			boolean hit = false;
			
			for(int i=0; i<word.length(); i++) {
				if(word.charAt(i) == upperChar) {
					hit = true;
					hits.setCharAt(i, upperChar);
				}
			}
			
			if(!hit)
				failsToLose--;
			
			return true;
		}
		else 
			return false;
	}
	
	@JsonIgnore
	public boolean isFinished() {
		return word.equals(hits.toString());
	}
	
	@JsonIgnore
	public boolean isGameOver() {
		return failsToLose == 0;
	}
	
	public GameUserView asUserView() {
		GameUserView.Builder builder = new GameUserView.Builder();
		
		builder.setGameId(gameId)
				.setState(state)
				.setHits(hits.toString())
				.setFinished(isFinished())
				.setFailsToLose(failsToLose)
				.setGameOver(isGameOver())
				.setLettersPlayed(lettersPlayed);
		
		if(isGameOver())
			builder.setSolution(word);
		
		return builder.build();	
	}
	
	public String toJson() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(this);
	}
}
