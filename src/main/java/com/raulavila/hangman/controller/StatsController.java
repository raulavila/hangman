package com.raulavila.hangman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.raulavila.hangman.repository.GameRepository;

@Controller
@RequestMapping("/stats")
public class StatsController {

	@Autowired
	GameRepository gameRepository;
	
	@RequestMapping(value="/show", method=RequestMethod.GET)
	public ModelAndView getStats() {
		ModelAndView mav = new ModelAndView("stats");

		//I think it would be better to return a GameStatsView class (not created yet),
		//but this is supposed to be an admin method, so there wouldn't be any problem
		mav.addObject("gameList", gameRepository.getGames());
		
		return mav;
	}
	
}
