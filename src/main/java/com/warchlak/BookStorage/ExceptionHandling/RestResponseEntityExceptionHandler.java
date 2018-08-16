package com.warchlak.BookStorage.ExceptionHandling;

import com.warchlak.BookStorage.ExceptionHandling.customExceptions.*;
import com.warchlak.BookStorage.configuration.EnglishMessageSource;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
	private static final String METHOD_HEADER_NAME = "Access-Control-Request-Method";
	
	private final EnglishMessageSource messageSource;
	
	@Autowired
	public RestResponseEntityExceptionHandler(EnglishMessageSource messageSource)
	{
		this.messageSource = messageSource;
	}
	
	@ExceptionHandler(value = {RestBadRequestException.class, BadHttpRequest.class})
	public ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request)
	{
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String body = messageSource.getCustomMessage("exception.BadRequest");
		return super.handleExceptionInternal(ex, buildResponseEntity(body, status),
				null, status, request);
	}
	
	@ExceptionHandler(MyHttpMethodNotSupportedException.class)
	public ResponseEntity<Object> handleMethodNotSupported(MyHttpMethodNotSupportedException ex, WebRequest request)
	{
		HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
		String body = messageSource.getCustomMessage("exception.MethodNotSupported", new Object[]{request
				.getHeader(METHOD_HEADER_NAME)});
		return super.handleExceptionInternal(ex, buildResponseEntity(body, status),
				null, status, request);
	}
	
	@ExceptionHandler(MyResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFound(MyResourceNotFoundException ex, WebRequest request)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		String body = ex.getMessage();
		return super.handleExceptionInternal(ex, buildResponseEntity(body, status),
				null, status, request);
		
	}
	
	@ExceptionHandler(MyInternalServerErrorException.class)
	public ResponseEntity<Object> handleInternalError(MyInternalServerErrorException ex, WebRequest webRequest)
	{
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		String body = messageSource.getCustomMessage("exception.InternalServerError");
		
		return super.handleExceptionInternal(ex, buildResponseEntity(body, status),
				null, status, webRequest);
	}
	
	@ExceptionHandler(InvalidIdState.class)
	public ResponseEntity<Object> handleBadId(InvalidIdState ex, WebRequest webRequest)
	{
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		String body = ex.getMessage();
		
		return super.handleExceptionInternal(ex, buildResponseEntity(body, status),
				null, status, webRequest);
	}
	
	private ErrorResponseEntity buildResponseEntity(String message, HttpStatus status)
	{
		Date date = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime();
		return new ErrorResponseEntity(message, status.value(), date);
	}
}
