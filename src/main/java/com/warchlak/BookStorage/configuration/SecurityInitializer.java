package com.warchlak.BookStorage.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.servlet.ServletContext;

@Configuration
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer
{
	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext)
	{
		insertFilters(servletContext, new MultipartFilter());
	}
}
