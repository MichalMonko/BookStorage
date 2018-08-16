package com.warchlak.BookStorage.ExceptionHandling.customExceptions;

public class InvalidIdState extends RuntimeException
{
	public InvalidIdState(String message)
	{
		super(message);
	}
}
