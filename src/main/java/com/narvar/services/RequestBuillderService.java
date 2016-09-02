package com.narvar.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.narvar.exceptions.types.RestGenericException;

@Service
/*
 * Decorator CLass not used presently Kept for Design perspetive
 * 
 * 
 */

public class RequestBuillderService {

	/*
	 * RequestBuillderService classs will ideally have helper classes to
	 * validate and process incoming request and based on the request type will
	 * inoke - data layer to get data from other sources/resources i.e. DB or
	 * external serivces Created skeloton methods to process all aspect of
	 * request i.e. request parameter, body ..etc
	 */

	HashMap request = new HashMap<String, Object>();

	public RequestBuillderService buildRequestMap() throws RestGenericException {
		request = new HashMap<String, Object>();
		return this;
	}

	public RequestBuillderService addRequestBody(Map<String, Object> requestBody) {
		checkRequest();
		request.put(RequestSegment.Body.name(), requestBody);
		return this;
	}

	public RequestBuillderService addHeader(Map<String, Object> header) throws RestGenericException {
		checkRequest();
		request.put(RequestSegment.Header.name(), header);
		return this;
	}

	public RequestBuillderService addRequestParams(Map<String, Object> requestParams) throws RestGenericException {
		checkRequest();
		request.put(RequestSegment.RequestParams.name(), requestParams);
		return this;
	}

	public RequestBuillderService addPathParams(Map<String, Object> pathParams) throws RestGenericException {
		checkRequest();
		request.put(RequestSegment.PathParams.name(), pathParams);
		return this;
	}

	private void checkRequest() {
		if (request == null)
			throw new IllegalStateException("Call buildRequestMap() before adding segments.");
	}

	public Map<String, Object> get() {
		//
		return request;
	}

	// This method will ideally delegate work
	// not being used currently
	public RequestBuillderService proccessRequest(Map<String, Object> request) throws RestGenericException {
		// TODO
		// validate the request type
		// Based on request validate the content
		// Decorate teh request for further processing if required
		// if we are expecting Etags , we can do validation
		request.put("isValidRequestt", "Valid");
		// send request for further processing

		return new RequestBuillderService();

	}

}
