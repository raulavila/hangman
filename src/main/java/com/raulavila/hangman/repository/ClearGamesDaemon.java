package com.raulavila.hangman.repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.raulavila.hangman.util.Constants;

//This daemon cleans games active for more than 1 day. In that case the cookie is no longer in the client,
//so it doesn't make any sense to keep those games in the repository
@Component
public class ClearGamesDaemon extends Thread {

	@Autowired
	GameRepository gameRepository;
	
	private long lapse = Constants.MAX_GAME_AGE_MS;

	private volatile boolean keepRunning = true;

	public void run() {
		while (keepRunning) {
			gameRepository.clearExpiredGames();
			
			try {				
				Thread.sleep(lapse);
			} catch (Exception e) {
				System.out.println("Daemon thread interrupted "+e.getMessage());
				keepRunning = false;
			}
		}

	}
	
	@PostConstruct
	public void start() {
		super.start();
	}

	@PreDestroy
	public void gracefullyStop() {
		keepRunning = false;
		this.interrupt();
	}

}
