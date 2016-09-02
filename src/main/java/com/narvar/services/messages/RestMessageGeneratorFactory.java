package com.narvar.services.messages;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public class RestMessageGeneratorFactory {

	private final String JSONTYPE = "JSON";
	private final String XMLTYPE = "XML";

	public ResponseEntity<Object> getMessageGenerator(String messageType, Map<String, Object> processedRequest,
			String url) {
		if (JSONTYPE.equals(messageType))
			return new RestJsonMessage().generateMessage(processedRequest, url);

		else if (XMLTYPE.equals(messageType))
			return new RestXmlMessage().generateMessage(processedRequest, url);
		else
			// TODO should thorugh message not supproeted Exception
			// this block is not reachable ideally

			return null;

	}

}
