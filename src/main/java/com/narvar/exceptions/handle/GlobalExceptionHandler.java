package com.narvar.exceptions.handle;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.narvar.exceptions.ErrorCodes;
import com.narvar.exceptions.RestMessageObject;
import com.narvar.exceptions.types.RestGenericException;

/*
 * Primary Class which advises on the Global Exception Handling 
 */
@RestController
@ControllerAdvice

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements ErrorCodes {

	/*
	 * Business Requirement 3.1 Invlaid requests
	 */

	@Override
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)

	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		// part of refernce code ... to be updated
		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final RestMessageObject errObj = new RestMessageObject(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				errors);
		return handleExceptionInternal(ex, errObj, headers, errObj.getStatus(), request);
	}

	@Override
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)

	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		// part of refernce code ... to be updated

		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final RestMessageObject errObj = new RestMessageObject(HttpStatus.BAD_REQUEST,
				"GlobalExceptionHandler reported>>" + ex.getLocalizedMessage(), errors);
		return handleExceptionInternal(ex, errObj, headers, errObj.getStatus(), request);
	}

	@Override
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)

	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final StringBuffer str = new StringBuffer(200);
		str.append("Exception Value" + ex.getValue())
				.append("Exception Property " + "GlobalExceptionHandler reported>>" + ex.getPropertyName())
				.append("Expected is " + ex.getRequiredType());

		final RestMessageObject errObj = new RestMessageObject(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				str.toString());
		return new ResponseEntity<Object>(errObj, new HttpHeaders(), errObj.getStatus());
	}

	@Override
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)

	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getRequestPartName() + " part of a multipart/form-data request  is missing";

		final RestMessageObject errObj = new RestMessageObject(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				"GlobalExceptionHandler reported>>" + error);
		return new ResponseEntity<Object>(errObj, new HttpHeaders(), errObj.getStatus());
	}

	@Override
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)

	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getParameterName() + " Request Parameter  is missing";
		final RestMessageObject errObj = new RestMessageObject(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(errObj, new HttpHeaders(), errObj.getStatus());
	}

	//
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

		final RestMessageObject errObj = new RestMessageObject(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(errObj, new HttpHeaders(), errObj.getStatus());
	}

	/*
	 * Business requirements 3.2
	 */

	// 404 invliadHandlers

	@Override
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		final RestMessageObject errObj = new RestMessageObject(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(errObj, new HttpHeaders(), errObj.getStatus());
	}

	/*
	 * Business Requirement 3.3
	 */
	// 415 unsported media type error handling
	@Override
	@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//

		final RestMessageObject errObj = new RestMessageObject(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
				ex.getLocalizedMessage(),  "GlobalExceptionHandler reported >>Application only Supports JSON/XML content type");
		return new ResponseEntity<Object>(errObj, new HttpHeaders(), errObj.getStatus());
	}

	/*
	 * // 500 internal Server Error type handling //INTERNAL_SERVER_ERROR
	 * 
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ RestGenericException.class })

	public ResponseEntity<Object> handleAll(final RestGenericException ex, final WebRequest request) {

		//
		ex.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		
		final RestMessageObject errObj = new RestMessageObject(HttpStatus.INTERNAL_SERVER_ERROR,
				ex.getLocalizedMessage(), "GlobalExceptionHandler reported>>Internal Server While request processing");

		return new ResponseEntity<Object>(errObj, new HttpHeaders(), errObj.getStatus());
	}

}
