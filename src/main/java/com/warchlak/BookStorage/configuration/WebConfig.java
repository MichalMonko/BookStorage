package com.warchlak.BookStorage.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
	
	@Bean
	LocaleResolver localeResolver()
	{
		LocaleResolver localeResolver = new SessionLocaleResolver();
		((SessionLocaleResolver) localeResolver).setDefaultLocale(Locale.US);
		return localeResolver;
	}
	
	private LocaleChangeInterceptor localeChangeInterceptor()
	{
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		return interceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	@Bean
	MessageSource messageSource()
	{
		MessageSource messageSource = new ResourceBundleMessageSource();
		((ResourceBundleMessageSource) messageSource).setBasenames("messages", "exceptions");
		((ResourceBundleMessageSource) messageSource).setDefaultEncoding("UTF-8");
		((ResourceBundleMessageSource) messageSource).setFallbackToSystemLocale(true);
		
		return messageSource;
	}
}
