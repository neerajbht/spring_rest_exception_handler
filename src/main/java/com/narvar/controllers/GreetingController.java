package com.narvar.controllers;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.narvar.exceptions.ErrorCodes;
import com.narvar.services.MessageResponseServices;
import com.narvar.services.RequestBuillderService;

@RestController
public class GreetingController implements ErrorCodes{

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	RequestBuillderService requestBuilderSerive;
	@Autowired
	MessageResponseServices responseMessageService;

	@RequestMapping(path = "/greeting", method = RequestMethod.GET)

	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) throws Exception {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	/*
	 * creating sample method to throw internal Server error
	 */
	@RequestMapping(path = "/greetingEx", method = RequestMethod.GET)

	public Greeting greeting() throws Exception {

		throw new Exception("method to throw the exception handledd by Exception handler");
	}

	/*
	 * creating a method to mehtod to emulate unsported media type
	 */
	@RequestMapping(path = "/greetingException", method = RequestMethod.POST)

	public Greeting postGreetingMessage() throws Exception {

		throw new Exception("method to throw the exception handledd by Exception handler");
	}

	@RequestMapping(path = "/jsonRequest", method = RequestMethod.GET)
	public ResponseEntity<Object> getGgreetingRes(@PathVariable Map<String, Object> pathParamMap,
			@RequestParam Map<String, Object> reqParamMap, @RequestHeader Map<String, Object> headerMap,
			HttpServletRequest request) throws Throwable {

		Map<String, Object> processedRequest = requestBuilderSerive.buildRequestMap().addHeader(headerMap)
				.addPathParams(pathParamMap).addRequestParams(reqParamMap).get();

		String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

		return (ResponseEntity<Object>) responseMessageService.processRequest(processedRequest, url, RequestMethod.PUT);
	}

	/*
	 * cereating method to emualte the case for unsupported Media type
	 */

	@RequestMapping(path = "/jsonRequestEx", method = RequestMethod.POST)
	public ResponseEntity<Object> geJsonExcetion(@PathVariable Map<String, Object> pathParamMap,
			@RequestParam Map<String, Object> reqParamMap, @RequestHeader Map<String, Object> headerMap,
			HttpServletRequest request) throws Throwable {

		throw new HttpMediaTypeNotSupportedException(CUSTOM_EXCEPTION_2);

	}
	
	/*
	 * method to get XML response 
	 */

	@RequestMapping(path = "/xmlRequest", method = RequestMethod.GET)
	public ResponseEntity<Object> getGgreetingResXml(@PathVariable Map<String, Object> pathParamMap,
			@RequestParam Map<String, Object> reqParamMap, @RequestHeader Map<String, Object> headerMap,
			HttpServletRequest request) throws Throwable {

		Map<String, Object> processedRequest = requestBuilderSerive.buildRequestMap().addHeader(headerMap)
				.addPathParams(pathParamMap).addRequestParams(reqParamMap).get();

		String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		return (ResponseEntity<Object>) responseMessageService.processRequest(processedRequest, url, RequestMethod.PUT);
	}
	
	/*
	 * method to emulate No Handle Excpetion
	 */
	@RequestMapping(path = "/xmlRequestEx", method = RequestMethod.POST)
	public ResponseEntity<Object> getGgreetingResXmlEx(@PathVariable Map<String, Object> pathParamMap,
			@RequestParam Map<String, Object> reqParamMap, @RequestHeader Map<String, Object> headerMap,
			HttpServletRequest request) throws Throwable {
		String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		 
		throw new NoHandlerFoundException(CUSTOM_EXCEPTION_1, url, null);
		
		
		
 	
	}

}
