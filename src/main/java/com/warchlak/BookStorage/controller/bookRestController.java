package com.warchlak.BookStorage.controller;

import com.warchlak.BookStorage.entity.Book;
import com.warchlak.BookStorage.repositoriy.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class bookRestController
{
	private final BookRepository repository;
	
	@Autowired
	public bookRestController(BookRepository repository)
	{
		this.repository = repository;
	}
	
	@RequestMapping("/getAll")
	public List<Book> getAllBooks()
	{
		return repository.findAll();
	}
}
