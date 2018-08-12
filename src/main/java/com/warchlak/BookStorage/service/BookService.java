package com.warchlak.BookStorage.service;

import com.warchlak.BookStorage.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService
{
	public Book findOneById(int id);
	
	public List<Book> findAll();
	
	public List<Book> findAllByTitle(String title);
	
	public Book save(Book book);
	
	public Book updateById(int id);
	
	public void deleteById(int id);
}
