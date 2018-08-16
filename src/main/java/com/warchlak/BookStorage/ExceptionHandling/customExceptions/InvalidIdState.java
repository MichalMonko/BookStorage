package com.warchlak.BookStorage.ExceptionHandling.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidIdState extends RuntimeException
{
	public InvalidIdState(String message)
	{
		super(message);
	}
}
