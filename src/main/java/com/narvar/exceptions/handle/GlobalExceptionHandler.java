package com.narvar.exceptions.handle;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.narvar.exceptions.ErrorCodes;
import com.narvar.exceptions.RestMessageObject;

/*
 * Primary Class which advises on the Global Exception Handling 
 */
@Controller
@ControllerAdvice(annotations = RestController.class)

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler 
  implements ErrorCodes{

	/*
	 * Business Requirement 3.1 Invlaid requests
	 */

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final RestMessageObject ErrorObj = new RestMessageObject(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				errors);
		return handleExceptionInternal(ex, ErrorObj, headers, ErrorObj.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final RestMessageObject ErrorObj = new RestMessageObject(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				errors);
		return handleExceptionInternal(ex, ErrorObj, headers, ErrorObj.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type "
				+ ex.getRequiredType();

		final RestMessageObject ErrorObj = new RestMessageObject(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				error);
		return new ResponseEntity<Object>(ErrorObj, new HttpHeaders(), ErrorObj.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getRequestPartName() + " part is missing";
		final RestMessageObject ErrorObj = new RestMessageObject(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				error);
		return new ResponseEntity<Object>(ErrorObj, new HttpHeaders(), ErrorObj.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getParameterName() + " parameter is missing";
		final RestMessageObject ErrorObj = new RestMessageObject(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				error);
		return new ResponseEntity<Object>(ErrorObj, new HttpHeaders(), ErrorObj.getStatus());
	}

	//

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

		final RestMessageObject ErrorObj = new RestMessageObject(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				error);
		return new ResponseEntity<Object>(ErrorObj, new HttpHeaders(), ErrorObj.getStatus());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
			errors.add("Handler to check ConstraintViolations");
		}

		final RestMessageObject ErrorObj = new RestMessageObject(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				errors);
		return new ResponseEntity<Object>(ErrorObj, new HttpHeaders(), ErrorObj.getStatus());
	}

	/*
	 * Business requirements 3.2
	 */

	// 404 invliadHandlers

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		final RestMessageObject ErrorObj = new RestMessageObject(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(ErrorObj, new HttpHeaders(), ErrorObj.getStatus());
	}
	/*
	 * Business Requirement 3.3
	 */
	// 415 unsported media type error handling

	@Override
 	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

		final RestMessageObject ErrorObj = new RestMessageObject(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
				ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
		return new ResponseEntity<Object>(ErrorObj, new HttpHeaders(), ErrorObj.getStatus());
	}

	/*
	 * // 500 internal Server Error type handling //INTERNAL_SERVER_ERROR
	 * 
	 */

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
		logger.info(ex.getClass().getName());
		logger.error("error", ex);
		//
		final RestMessageObject ErrorObj = new RestMessageObject(HttpStatus.INTERNAL_SERVER_ERROR,
				ex.getLocalizedMessage(), "Internal Server Error occured");

		return new ResponseEntity<Object>(ErrorObj, new HttpHeaders(), ErrorObj.getStatus());
	}

}
