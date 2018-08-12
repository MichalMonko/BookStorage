package com.warchlak.BookStorage.ExceptionHandling.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class MyHttpMethodNotSupportedException extends RuntimeException
{
	public MyHttpMethodNotSupportedException()
	{
	}
	
	public MyHttpMethodNotSupportedException(String message)
	{
		super(message);
	}
}
