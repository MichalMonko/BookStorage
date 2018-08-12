package com.warchlak.BookStorage.util;

import com.warchlak.BookStorage.ExceptionHandling.customExceptions.MyResourceNotFoundException;


public class NullChecker
{
	public static <T> T checkForNull(T object)
	{
		if(object == null)
		{
			throw new MyResourceNotFoundException();
		}
		
		return object;
	}
}
