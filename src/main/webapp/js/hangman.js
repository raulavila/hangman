(function($) {
	
var hangman = {};

hangman.gameState = null;
hangman.listenKeyboard = true;
	
hangman.testAlphabetic = function testAlphabetic(str){
			return /^[a-zA-Z]$/.test(str);
};

hangman.initKeyboard = function() {
	 jsKeyboard.init("virtualKeyboard");
		
     var $firstInput = $('#keyPressed').first();
     jsKeyboard.currentElement = $firstInput;
     jsKeyboard.currentElementCursorPosition = 0;
     
     $("#keyPressed").on("change" , function(){
    	 
    	 //We don't listen events to the keyboard while waiting for server response,
    	 //to avoid synchronicity problems
    	 if(!hangman.listenKeyboard) {
    		 $("#keyPressed").val("");
    		 return;
    	 }
    	 
    	 if(!hangman.testAlphabetic($("#keyPressed").val())) {
			alert("Only alphabetical characters allowed in this game!");
		}
		else {
			var character = $("#keyPressed").val(); 
			$("#keyPressed").val("");
			hangman.bet(character);			
		}
			
	
	 });
};

hangman.initGame = function () {
	$.ajax({
	    type: "GET",
	    url: webroot+"/game/state",	    
	    dataType: "json",
	    success: function(data) {
	        hangman.gameState = data;
	        
	        $(".loading").hide();
	        
	        hangman.showState();
	        
	        $("#game").show();
	        $("#keyboardWrapper").removeClass("hidden");
	    },
	    error: function(){
	        alert("There was a problem initializing the game!!")
	    }
	});
	
};

hangman.showState = function() {
	var state = hangman.gameState;
	
	$("#failsToLose").html(state.failsToLose);
	
	var letters = "";
	$.each(state.lettersPlayed, function(index, value){
		letters = letters + " <b>" + value+"</b>";		
	});
	$("#lettersPlayed").html(letters);
	
	var wordPlayed = state.hits;
	
	if(state.finished) {  //Win!
		$("#keyboardWrapper").addClass("hidden");
		$("#win").show();
	}
	else if(state.gameOver) {
		$("#keyboardWrapper").addClass("hidden");
		$("#gameOver").show();
		
		$("#hits").removeClass("green");
		$("#hits").addClass("red");
		
		wordPlayed = state.solution;
	}
		
	$("#hits").empty();
	for( var i = 0; i < wordPlayed.length; i++) {
		var character = wordPlayed.charAt(i);		
		$("<span>")
			.addClass("letterHit")
			.html(character)
			.appendTo($("#hits"));
	}
	
};

hangman.bet = function(character) {
	
	character = character.toUpperCase();
	
	if($.inArray(character, hangman.gameState.lettersPlayed) >= 0) {
		alert("Again? Really? I don't think so...");
		return;
	}
	
	hangman.waitingServer();
	
	hangman.gameState.character = character;
	
	$.ajax({
	    type: "PUT",
	    url: webroot+"/game/bet",	    
	    contentType: "application/json",
	    data: JSON.stringify(hangman.gameState), 
	    dataType: "json",
	    success: function(data) {	    		    	
	    	if(data.state != hangman.gameState.state + 1) {
	        	alert("Synchronizity problem! Are you playing in two different tabs? Otherwise, please restart.")
	        } 
	        else {
		    	hangman.gameState = data;
		        
		        hangman.showState();
	        }
	    	hangman.responseReceived();
	    },
	    statusCode: {
	    	400: function() {  //Bad request, non alphabetical character, for instance
	    		alert("Error! Wrong information sent to the server");
	    		hangman.responseReceived();
	    	},
	    	409: function() {  //Conflict, synchronizity problems
	    		alert("Synchronizity problem! Are you playing in two different tabs? Otherwise, please restart.");
	    		hangman.responseReceived();
	    	},
	    },
	    error: function(jqXHR, textStatus, errorThrown){
	    	if(jqXHR.status != 400 && jqXHR.status != 409)	    	
	    		alert("There was an unexpected problem sending the bet!!")
	    		hangman.responseReceived();
	    		
	    }
	});
	
};

hangman.waitingServer = function() {
	$("#waitingResponse").show();
	hangman.listenKeyboard = false;
};

hangman.responseReceived = function() {
	
	$("#waitingResponse").hide();
	hangman.listenKeyboard = true;
};
	
$(document).ready(function(){
	
	
	/*
	 * I have used the jsKeyboard plugin (http://www.jquery4u.com/demos/onscreenkeyboard/),
	 * changing its behaviour slightly to adapt to the requirements of the game
	 */
	hangman.initKeyboard();
	
	hangman.initGame();
	
	$("#restart").click(function(){
		document.location.href = webroot + "/game/reset";
	});
	
});
	
	
})(jQuery);