package com.raulavila.hangman.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

//Word repository, for the purpose of the exercise I think it's enough to add several hard-coded words to them 
//A better solution would be creating a file with words and loading them during context creation
@Repository
public class WordDaoImpl implements WordDao {

	private List<String> wordList = new ArrayList<String>();
	private Random random = new Random();
	
	@Override
	public String getRandomWord() {
		return wordList.get(random.nextInt(wordList.size()));
	}
	
	@PostConstruct
	private void readWords() {
		wordList.add("TABLE");
		wordList.add("HOUSE");
		wordList.add("WINDOW");
		wordList.add("COMPUTER");
		wordList.add("SHELF");
		wordList.add("DRAWER");
		wordList.add("PENCIL");
		wordList.add("MOTORCYCLE");
	}
	
}
