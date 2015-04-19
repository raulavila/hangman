package com.raulavila.hangman.util;

public class Constants {

	public static final String WEBROOT = "/hangman";
	public static final int MAX_COOKIE_AGE =  24 * 60 * 60;  //1 day
	public static final int MAX_GAME_AGE_MS = MAX_COOKIE_AGE * 1000;
	public static final String GAME_ID_COOKIE_NAME = "hangmanGameId";

	public static final String UNEXPECTED_SERVER_ERROR = "There was an unexpected error in the server";
	public static final String CONCURRENT_GAMES_ERROR = "Synchronizity error. It seems to be several users playing the same game.";
	public static final String INCORRECT_PLAY_ERROR = "Incorrect play request";
	
	public static final String UTF_8 = "UTF-8";
	
	//Non instantiable
	private Constants() {
		throw new AssertionError();
	}
}
