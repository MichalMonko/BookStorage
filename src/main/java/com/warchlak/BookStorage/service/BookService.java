package com.warchlak.BookStorage.service;

import com.warchlak.BookStorage.entity.Book;

import java.util.List;

public interface BookService
{
	public Book findOneById(int id);
	
	public List<Book> findAll();
	
	public List<Book> findAllByTitle(String title);
	
	public Book save(Book book);
	
	public Book update(Book book);
	
	public void deleteById(int id);
}
