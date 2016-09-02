package com.narvar.services;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import com.narvar.exceptions.types.RestGenericException;
import com.narvar.services.messages.RestMessageGeneratorFactory;

import net.minidev.json.JSONObject;

@Service
/*
 * Message Provider Class 
 * Can be extended to provide other response message types in future 
 * i.e live Feeds , Documents, Messages from Queues ..etc
 */
public class MessageResponseServices {

	public ResponseEntity<Object> processRequest(Map<String, Object> processedRequest, String url, RequestMethod put)
		throws RestGenericException
	  {
		// TODO Auto-generated method stub
		boolean generateJsonMessage = false;
		JSONObject response = null;
		ResponseEntity<Object> validationResponse = null;

		// veriy request for kind of processing requied
		// determin the messageformater requrired
		// genrate message
		// Create a JSON obhet or XML object for respponse

		RestMessageGeneratorFactory messageFactory = new RestMessageGeneratorFactory();

		generateJsonMessage = true;
		if (url.contains("xmlRequest")) {
 
			validationResponse = messageFactory.getMessageGenerator("XML", processedRequest, url);
		} else if (url.contains("jsonRequest")) {
			validationResponse = messageFactory.getMessageGenerator("JSON", processedRequest, url);

		}

		else {
			// message FormatNotSupportedCurrnetly
			// throw Exception
			// TOTDO message format not supported

		}

		return validationResponse;
	}

}
