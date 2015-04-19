package com.raulavila.hangman.util;

import java.net.URL;
import java.net.URLDecoder;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class FileUtils {

	private String classPathFolder;
	
	@PostConstruct
	public void init() {
		this.classPathFolder = this.getFolder();
	}
	
	//Get WEB-INF/classes folder
	private String getFolder() {
		
		try {
			URL r = this.getClass().getResource("/");
			String folder = URLDecoder.decode(r.getFile(), Constants.UTF_8);
			if (folder.startsWith("/")) {
				folder = folder.replaceFirst("/", "");
			}
			
			return folder;
		}
		catch(Exception e) {
			
			//This shouldn't happen, but there is nothing we can do here
			System.out.println("Problem searching the classpath folder: "+e.getMessage());
			return "";
		}
	}

	public String getClassPathFolder() {
		return classPathFolder;
	}
	
	
	
}
