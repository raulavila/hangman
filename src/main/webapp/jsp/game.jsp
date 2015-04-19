<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="webroot" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>Hangman</title>

<link rel="stylesheet" href="${webroot}/css/jsKeyboard.css" type="text/css" />
<link rel="stylesheet" href="${webroot}/css/hangman.css" type="text/css" />

</head>

<body>
    <div id="body-wrapper">

        <h1>Hangman</h1>

		<div class="loading"></div>
		
		<div id="game" class="hidden">
			<button id="restart">Restart / New game</button>
			
			<div class="clear"></div>
			
			<div>Steps to lose: <span id="failsToLose"></span></div>
			
			<div class="clear"></div>						
			
			<div>Letters played: <span id="lettersPlayed"></span></div>		
			
			<div class="clear"></div>
			
			<div id="hits" class="green"></div>
			
			<div class="clear"></div>						
			
			<div id="gameOver" class="hidden red">Game Over</div>
			<div id="win" class="hidden green">Congratulations!!</div>
		</div>
		
		<div id="keyboardWrapper" class="hidden">
				<hr />								         								
				<div id="virtualKeyboard"></div>
				<div id="waitingResponse" class="loading hidden"></div>
		</div>

    </div>
    
    <input id="keyPressed" name="keyPressed" type="text" maxlength="1" class="hidden" value="" />                        

	<script src="${webroot}/js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${webroot}/js/jsKeyboard.js" type="text/javascript"></script>
	<script src="${webroot}/js/hangman.js" type="text/javascript"></script>
	
	<script>
		var webroot = "${webroot}";
	</script>

</body>
</html>

