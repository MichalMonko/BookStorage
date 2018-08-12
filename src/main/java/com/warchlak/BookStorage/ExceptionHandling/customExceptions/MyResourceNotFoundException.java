package com.warchlak.BookStorage.ExceptionHandling.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyResourceNotFoundException extends RuntimeException
{
	public MyResourceNotFoundException()
	{
	}
	
	public MyResourceNotFoundException(String message)
	{
		super(message);
	}
}
