package com.warchlak.BookStorage.service;

import com.warchlak.BookStorage.ExceptionHandling.customExceptions.MyResourceNotFoundException;
import com.warchlak.BookStorage.configuration.EnglishMessageSource;
import com.warchlak.BookStorage.entity.Book;
import com.warchlak.BookStorage.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IBookService implements BookService
{
	private final BookRepository repository;
	
	private final EnglishMessageSource messageSource;
	
	@Autowired
	public IBookService(BookRepository repository, EnglishMessageSource messageSource)
	{
		this.repository = repository;
		this.messageSource = messageSource;
	}
	
	@Override
	public Book findOneById(int id)
	{
		Optional<Book> bookWrapper = repository.findById(id);
		if (bookWrapper.isPresent())
		{
			return bookWrapper.get();
		}
		
		throw new MyResourceNotFoundException(messageSource.getCustomMessage
				("exception.ResourceNotFound"));
	}
	
	@Override
	public List<Book> findAll()
	{
		return repository.findAll();
	}
	
	public List<Book> findAllByTitle(String title)
	{
		return repository.findAllByTitleIgnoreCase(title);
	}
	
	@Override
	public Book save(Book book)
	{
		return repository.save(book);
	}
	
	@Override
	public Book updateById(int id)
	{
		Optional<Book> bookWrapper = repository.findById(id);
		if (bookWrapper.isPresent())
		{
			return repository.save(bookWrapper.get());
		}
		else
		{
			throw new MyResourceNotFoundException(
					messageSource.getCustomMessage(
							"exception.ResourceNotFound"));
		}
	}
	
	@Override
	public void deleteById(int id)
	{
		repository.deleteById(id);
	}
}
