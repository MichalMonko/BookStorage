package com.warchlak.BookStorage.ExceptionHandling.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MyInternalServerErrorException extends RuntimeException
{
	public MyInternalServerErrorException()
	{
	}
	
	public MyInternalServerErrorException(String message)
	{
		super(message);
	}
}
