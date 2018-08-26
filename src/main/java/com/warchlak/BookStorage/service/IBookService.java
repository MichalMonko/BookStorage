package com.warchlak.BookStorage.service;

import com.warchlak.BookStorage.ExceptionHandling.customExceptions.InvalidIdState;
import com.warchlak.BookStorage.ExceptionHandling.customExceptions.MyResourceNotFoundException;
import com.warchlak.BookStorage.configuration.EnglishMessageSource;
import com.warchlak.BookStorage.entity.Book;
import com.warchlak.BookStorage.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class IBookService implements BookService
{
	private final BookRepository repository;
	
	private final EnglishMessageSource messageSource;
	
	private final FileSystemStorageService fileSystemStorageService;
	
	@Autowired
	public IBookService(BookRepository repository, EnglishMessageSource messageSource,
	                    FileSystemStorageService fileSystemStorageService)
	{
		this.repository = repository;
		this.messageSource = messageSource;
		this.fileSystemStorageService = fileSystemStorageService;
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
		if (book.getId() != null && book.getId() != 0)
		{
			throw new InvalidIdState(messageSource.getCustomMessage("exception.InvalidIdState.idShouldBeNull"));
		}
		return repository.save(book);
	}
	
	@Override
	public Book update(Book book)
	{
		
		if (book.getId() == null)
		{
			throw new InvalidIdState(messageSource.getCustomMessage("exception.InvalidIdState.IdShouldNotBeNull"));
		}
		else if (!repository.existsById(book.getId()))
		{
			throw new MyResourceNotFoundException(
					messageSource.getCustomMessage(
							"exception.ResourceNotFound"));
		}
		{
			return repository.save(book);
		}
	}
	
	@Override
	public void deleteById(int id)
	{
		try
		{
			Book book = repository.getOne(id);
			String imageFilename = book.getImageLink();
			
			if (null != imageFilename)
			{
				try
				{
					fileSystemStorageService.delete(imageFilename);
				} catch (IOException e)
				{
					Logger.getLogger(this.getClass().getName()).info("File " + imageFilename + " was already deleted");
				}
			}
			
			repository.deleteById(id);
		} catch (EntityNotFoundException e)
		{
			throw new MyResourceNotFoundException(
					messageSource.getCustomMessage(
							"exception.ResourceNotFound") + e.getLocalizedMessage());
		}
	}
}
