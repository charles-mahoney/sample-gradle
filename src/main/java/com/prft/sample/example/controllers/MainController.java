package com.prft.sample.example.controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prft.sample.example.services.SimpleService;
import com.prft.sample.example.utils.Utils;

import net.logstash.logback.marker.Markers;

@RestController
public class MainController {
	static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@Autowired
	SimpleService simpleService;
	
	@Autowired
	Utils utils;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String getbackend() {
		LOGGER.info("Calling Controller");
		return "HELLO WORLD";
		
	}
	
	@RequestMapping("/properties")
	public String getproperties(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, String> loggingAttributes = new HashMap<String, String>();
		
		loggingAttributes.put("customAttribute","MyCustomValue");
		loggingAttributes.put("requestURL", request.getRequestURL().toString());
		
		
		
		LOGGER.info(Markers.appendEntries(loggingAttributes).and(Markers.append("simpleService", simpleService)), "SUCCESS");
		
		return "Success Updated 0740";
		
	}
	
	@RequestMapping(value="/**/",method=RequestMethod.GET)
	public ResponseEntity<String> MainResponse(HttpServletRequest request){
	    String uri = request.getRequestURI();
		
		String returnStr = null;
		try {
			returnStr = utils.getFile(uri);
		} catch (IOException e) {
			LOGGER.error("Error finding file for URI: " + uri + " ErrorMessage: " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		
		LOGGER.info("Returning for URI: "+uri);
		return new ResponseEntity<String>(returnStr,HttpStatus.OK);
	}
}
