package com.raulavila.hangman.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import com.raulavila.hangman.factory.GameFactory;
import com.raulavila.hangman.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackupUtils {
	
	@Autowired
	GameFactory gameFactory;
	
	@Autowired
	FileUtils fileUtils;
	
	//Saves the collection of games parsed as JSON in the classpath
	public void saveObjectList(String fileName, Collection<Game> objects) {
		try {
			File file = new File(fileUtils.getClassPathFolder(), fileName);
			PrintWriter writer = new PrintWriter(file);
			
			for(Game game: objects)
				writer.write(game.toJson());
			
			writer.close();
		}
		catch(Exception e){
			
			//Bad luck if we fail to backup the games
			System.out.println("Error saving existent games "+e.getMessage());
		}
		
	}
	
	
	//Restores the backup collection of games
	public Collection<Game> getGamesBackup(String fileName) {
		Collection<Game> games = new ArrayList<Game>();
		
		File file;
		
		try {
			file = new File(fileUtils.getClassPathFolder(), fileName);
		}
		catch(Exception e){
			file = null;
		}
		
		if(file==null || !file.exists() || !file.isFile())
			return games;
		
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
				games.add(gameFactory.getGameFromJson(line));
			}
		}
		catch(Exception e) {
			//Bad luck if we fail to backup the games
			System.out.println("Error saving existent games "+e.getMessage());
			
		}
		
		//We return everything we've been able to load from file
		return games;
		
	}
	
	

}
