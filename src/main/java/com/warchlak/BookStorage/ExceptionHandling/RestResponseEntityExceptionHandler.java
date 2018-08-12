package com.warchlak.BookStorage.ExceptionHandling;

import com.warchlak.BookStorage.ExceptionHandling.customExceptions.MyHttpMethodNotSupportedException;
import com.warchlak.BookStorage.ExceptionHandling.customExceptions.MyInternalServerErrorException;
import com.warchlak.BookStorage.ExceptionHandling.customExceptions.MyResourceNotFoundException;
import com.warchlak.BookStorage.ExceptionHandling.customExceptions.RestBadRequestExcetpion;
import com.warchlak.BookStorage.configuration.EnglishMessageSource;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
	
	@ExceptionHandler(value = {RestBadRequestExcetpion.class, BadHttpRequest.class})
	public ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request)
	{
		String body = messageSource.getCustomMessage("exception.BadRequest");
		return super.handleExceptionInternal(ex, body, null, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(MyHttpMethodNotSupportedException.class)
	public ResponseEntity<Object> handleMethodNotSupported(MyHttpMethodNotSupportedException ex, WebRequest request)
	{
		String body = messageSource.getCustomMessage("exception.MethodNotSupported", new Object[]{request
				.getHeader(METHOD_HEADER_NAME)});
		return super.handleExceptionInternal(ex, body, null, HttpStatus.METHOD_NOT_ALLOWED, request);
	}
	
	@ExceptionHandler(MyResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFound(MyResourceNotFoundException ex, WebRequest request)
	{
		String body = messageSource.getCustomMessage("exception.ResourceNotFound");
		return super.handleExceptionInternal(ex, body, null, HttpStatus.NOT_FOUND, request);
		
	}
	
	@ExceptionHandler(MyInternalServerErrorException.class)
	public ResponseEntity<Object> handleInternalError(MyInternalServerErrorException ex, WebRequest webRequest)
	{
		String body = messageSource.getCustomMessage("exception.InternalServerError");
		return super.handleExceptionInternal(ex, body, null, HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
	}
}
