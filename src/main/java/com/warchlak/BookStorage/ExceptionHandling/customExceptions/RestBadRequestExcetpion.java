package com.warchlak.BookStorage.ExceptionHandling.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RestBadRequestExcetpion extends RuntimeException
{
	public RestBadRequestExcetpion()
	{
	}
	
	public RestBadRequestExcetpion(String message)
	{
		super(message);
	}
}
