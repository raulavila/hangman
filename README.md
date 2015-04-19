#Hangman

Hangman game, which I implemented in March 2014 for a coding challenge.

##Technologies used:

* Spring MVC
* HTML
* CSS
* Jsp
* Ajax
* jQuery
* Plugin jQuery to show on-screen keyboard: http://www.jquery4u.com/demos/onscreenkeyboard/

##Word repository

The words are stored in an in-memory word repository (quite simple actually): WordDaoImpl, which implements the interface WordDao, so it would be quite easy to replace it for a database repository or similar

##Execution

Run class com.raulavila.hangman.main.EmbeddedJetty, and, using a browser, go to http://localhost:8080/game/play