package com.raulavila.hangman.views;

import java.util.List;

//Class used for server-client communication
public class GameUserView {
	private String gameId;
	private int state;
	private String hits;
	private boolean finished;
	private int failsToLose;
	private boolean gameOver;
	private String solution;
	private List<Character> lettersPlayed;
	
	private char character;
	
	public GameUserView() {}
	
	private GameUserView(Builder builder) {
		this.gameId = builder.gameId;
		this.state = builder.state;
		this.hits = builder.hits;
		this.finished = builder.finished;
		this.failsToLose = builder.failsToLose;
		this.gameOver = builder.gameOver;
		this.solution = builder.solution;
		this.lettersPlayed = builder.lettersPlayed;
	}
	
	public static class Builder {
		private String gameId;
		private int state;
		private String hits;
		private boolean finished;
		private int failsToLose;
		private boolean gameOver;
		private List<Character> lettersPlayed;
		private String solution;
		
		
		public Builder setGameId(String gameId) {
			this.gameId = gameId;
			return this;
		}
		public Builder setState(int state) {
			this.state = state;
			return this;
		}
		public Builder setHits(String hits) {
			this.hits = hits;
			return this;
		}
		public Builder setFinished(boolean finished) {
			this.finished = finished;
			return this;
		}
		public Builder setFailsToLose(int failsToLose) {
			this.failsToLose = failsToLose;
			return this;
		}
		public Builder setGameOver(boolean gameOver) {
			this.gameOver = gameOver;
			return this;
		}
		public Builder setSolution(String solution) {
			this.solution = solution;
			return this;
		}
		
		public Builder setLettersPlayed(List<Character> lettersPlayed) {
			this.lettersPlayed = lettersPlayed;
			return this;
		}
		
		public GameUserView build() {
			return new GameUserView(this);
		}
		
	}
	

	public String getGameId() {
		return gameId;
	}

	public int getState() {
		return state;
	}

	public String getHits() {
		return hits;
	}

	public boolean isFinished() {
		return finished;
	}

	public int getFailsToLose() {
		return failsToLose;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public String getSolution() {
		return solution;
	}

	public char getCharacter() {
		return character;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setHits(String hits) {
		this.hits = hits;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public void setFailsToLose(int failsToLose) {
		this.failsToLose = failsToLose;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
	

	public List<Character> getLettersPlayed() {
		return lettersPlayed;
	}

	public void setLettersPlayed(List<Character> lettersPlayed) {
		this.lettersPlayed = lettersPlayed;
	}

	public void setCharacter(char character) {
		this.character = character;
	}

}