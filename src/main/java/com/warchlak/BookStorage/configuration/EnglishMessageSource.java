package com.warchlak.BookStorage.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class EnglishMessageSource implements MessageSource
{
	private final MessageSource messageSource;
	
	@Autowired
	public EnglishMessageSource(MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}
	
	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale)
	{
		return messageSource.getMessage(code, args, defaultMessage, locale);
	}
	
	@Override
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException
	{
		return messageSource.getMessage(code, args, locale);
	}
	
	@Override
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException
	{
		return messageSource.getMessage(resolvable, locale);
	}
	
	public String getCustomMessage(String messageCode, Object[] args)
	{
		return getMessage(messageCode, args, Locale.US);
	}
	
	public String getCustomMessage(String messageCode)
	{
		return getMessage(messageCode, null, Locale.US);
	}
}
