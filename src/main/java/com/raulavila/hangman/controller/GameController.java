package com.raulavila.hangman.controller;

import javax.servlet.http.HttpServletResponse;

import com.raulavila.hangman.factory.GameFactory;
import com.raulavila.hangman.model.ErrorInfo;
import com.raulavila.hangman.views.GameUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.raulavila.hangman.model.Game;
import com.raulavila.hangman.repository.GameRepositoryImpl;
import com.raulavila.hangman.util.Constants;
import com.raulavila.hangman.util.CookieUtils;

//The state of the game is controlled using a cookie with the value of the gameId
@Controller
@RequestMapping("/game")
public class GameController {

	@Autowired
	GameFactory gameFactory;
	
	@Autowired
	GameRepositoryImpl gameRepository;
	
	@RequestMapping(value="/play", method=RequestMethod.GET)
	public String play() {
		return "game";
	}

	@RequestMapping(value="/reset", method=RequestMethod.GET)
	public String reset(@CookieValue(value="hangmanGameId", required=false) String gameId,					
						HttpServletResponse response) {

		if(gameId != null) {  // delete game with this cookie
			gameRepository.removeGame(gameId);
			CookieUtils.clearCookie(response, Constants.GAME_ID_COOKIE_NAME);
		}
		
		return "redirect:/game/play";
	}
	
	@RequestMapping(value="/state", method=RequestMethod.GET)
	public @ResponseBody
	GameUserView getState(@CookieValue(value="hangmanGameId", required=false) String gameId,
												HttpServletResponse response) {
		
		Game game = null;
		
		if(gameId != null) {
			game = gameRepository.getGame(gameId);
		}
		
		//This can happen if there is no cookie or the game is not active in the repository
		if(game == null) {
			game = gameFactory.createNewGame();
			gameRepository.addGame(game);
			CookieUtils.addCookie(response, Constants.GAME_ID_COOKIE_NAME, game.getGameId());
		}
		
		return game.asUserView();
	}
	
	
	@RequestMapping(value="/bet", method=RequestMethod.PUT)
	public @ResponseBody Object playGame(@CookieValue(value="hangmanGameId", required=false) String gameId,
												@RequestBody GameUserView gameUserView,
												HttpServletResponse response) {
		
		if(gameId == null)  //try to take the gameId from gameUserView
			gameId = gameUserView.getGameId();
		
		Game game = gameRepository.getGame(gameId);
		
		ErrorInfo errorInfo = null;
		
		if(game == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			errorInfo = new ErrorInfo(Constants.UNEXPECTED_SERVER_ERROR);
		}
		else {
			//Just in case the same user is playing in different tabs, check the state
			synchronized (game) {
				if(game.getState() != gameUserView.getState()) {
					response.setStatus(HttpServletResponse.SC_CONFLICT);
					errorInfo = new ErrorInfo(Constants.CONCURRENT_GAMES_ERROR);
				}
				else {
					if(!game.play(gameUserView.getCharacter())) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						errorInfo = new ErrorInfo(Constants.INCORRECT_PLAY_ERROR);
					}
					
					if(game.isGameOver() || game.isFinished()) {
						gameRepository.removeGame(game);
						CookieUtils.clearCookie(response, Constants.GAME_ID_COOKIE_NAME);
					}
				}
			}
		}
		
		if(errorInfo != null)
			return errorInfo;
		else
			return game.asUserView();
	}
}
