package com.narvar.services.messages;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.minidev.json.JSONObject;

public class RestJsonMessage implements RestMessageGenrator{

	@Override
	public ResponseEntity<Object> generateMessage(Map<String, Object> processedRequest, String url) 
	{

		// TODO
		// process /refine processReuqest of get a new object from other service
		JSONObject response = new JSONObject();

		response = new JSONObject();
		response.put("FName", "FirtName");
		response.put("LName", "Second Nmae");

		ResponseEntity<Object> validationResponse = new ResponseEntity<Object>(response, HttpStatus.OK);
		return validationResponse;
	}
}
