package com.narvar.services.messages;

import java.io.StringReader;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class RestXmlMessage implements RestMessageGenrator {

	// TODO
	@Override
	public ResponseEntity<Object> generateMessage(Map<String, Object> processedRequest, String url) {

		// TODO get the xmloutput from the class and add to Response entity

		// adding temporary sol.

		String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><a><b></b><c></c></a>";
		Document document = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(new InputSource(new StringReader(xmlString)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResponseEntity<Object> validationResponse = new ResponseEntity<Object>(document, HttpStatus.OK);
		return validationResponse;

	}
}
