package com.narvar.utils

import org.springframework.http.ResponseEntity;

import groovy.xml.MarkupBuilder;
import groovy.util.XmlSlurper;

class RestMessageXmlMarkupBuilder {

	//TODO - Groovy Based parsing engine
	// Template for xml creation
	public ResponseEntity<Object> generateXmlMessage(ResponseEntity<Object> obj,String URL) {

		def sw = new StringWriter()
		def xml = new MarkupBuilder(sw)
		//TODO
		// xml message based on the object Map
		//create temporary
		xml.records(){
			xml.response(message:"hi")
			xml.body(firstname:"FirstName",lastName:"LastName")
			xml.body(message:"Welcome")
		}

		def records = new XmlSlurper().parseText(sw.toString())
	}

}
