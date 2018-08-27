package com.warchlak.BookStorage.service;

import com.warchlak.BookStorage.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BookService
{
	public Book findOneById(int id);
	
	public List<Book> findAll();
	
	public List<Book> findAllByTitle(String title);
	
	public Book save(Book book);
	
	public Book update(Book book);
	
	public void deleteById(int id);
	
	public Page<Book> getPage(PageRequest pageRequest);
	
	Page<Book> getPageByAnyTag(String tagString, PageRequest pageRequest);
	
	Page<Book> getPageByAllTags(String tagString, PageRequest pageRequest);
}
