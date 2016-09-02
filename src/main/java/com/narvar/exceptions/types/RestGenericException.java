
package com.narvar.exceptions.types;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Core application exception class. Implementors need to use this in conjunction with error code mapping
 * defined in tenant configuration files.
 *
 */
public class RestGenericException extends Exception{
	
	private static final long serialVersionUID = -2997146606165029587L;
	private String errorCode;
	private Map<String, String> errorMap = new HashMap<String, String>();
	 
	
	public Map<String, String> getErrorMap() {
		return errorMap;
	}


	public void setErrorMap(Map<String, String> errorMap) {
		this.errorMap = errorMap;
	}


	public RestGenericException(String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}
	
	public RestGenericException(String errorCode, Throwable cause) {
		super(errorCode, cause);
		this.errorCode = errorCode;
	}
	
	public RestGenericException(Map<String, String> errorMap) {
		this.errorMap = errorMap;
	}
	
	public RestGenericException(Map<String, String> errorMap, Throwable cause) {
		super(cause);
		this.errorMap = errorMap;
	}

	public List<Map<String, String>> getErrorMapAsList() {
		return Arrays.asList(errorMap);
	}
	
	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
