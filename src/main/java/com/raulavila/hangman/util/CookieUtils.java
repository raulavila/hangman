package com.raulavila.hangman.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	public static void addCookie(HttpServletResponse response, String name, String value) {
		Cookie cookie = new Cookie(name, value); 
		cookie.setPath(Constants.WEBROOT);
		cookie.setMaxAge(Constants.MAX_COOKIE_AGE);
		response.addCookie(cookie); 
	}
	
	public static void clearCookie(HttpServletResponse response, String name) {
		Cookie cookie = new Cookie(name, null); 
		cookie.setPath(Constants.WEBROOT);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
}
