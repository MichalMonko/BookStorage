package com.warchlak.BookStorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BookStorageApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(BookStorageApplication.class, args);
	}
	
}
