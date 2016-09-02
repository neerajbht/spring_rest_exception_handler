package com.narvar.services.messages;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface RestMessageGenrator {

	public ResponseEntity<Object> generateMessage(Map<String, Object> processedRequest, String url);

}
