package com.warchlak.BookStorage;

import com.warchlak.BookStorage.repositoriy.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaRepositories
public class BookStorageApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(BookStorageApplication.class, args);
	}
	
}
