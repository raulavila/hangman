<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="webroot" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>Hangman</title>

<link rel="stylesheet" href="${webroot}/css/hangman.css" type="text/css" />

</head>

<body>
    <div id="body-wrapper">

        <h1>Hangman Stats</h1>
		
		<div id="stats">
		
		<table cellpadding="5">
		<thead>
		<tr>
			<th>Game ID</th>
			<th>Word</th>
			<th>Finish Date</th>
			<th>Hits</th>
			<th>Fails to lose</th>
		</tr>
		</thead>
		<tbody>
		
		<c:forEach items="${gameList}" var="game">
		<tr>
			<td>${game.gameId}</td>
			<td>${game.word}</td>
			<td>${game.finishDate}</td>
			<td>${game.hits}</td>
			<td>${game.failsToLose}</td>
		</tr>
		</c:forEach>
		
		</tbody>
		</table>
		
		</div>		

    </div>
                           
</body>
</html>

