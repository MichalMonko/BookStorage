package com.warchlak.BookStorage.service;

import com.warchlak.BookStorage.ExceptionHandling.customExceptions.InvalidIdState;
import com.warchlak.BookStorage.ExceptionHandling.customExceptions.MyResourceNotFoundException;
import com.warchlak.BookStorage.configuration.EnglishMessageSource;
import com.warchlak.BookStorage.entity.Book;
import com.warchlak.BookStorage.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
	public Page<Book> getPage(PageRequest pageRequest)
	{
		return repository.findAll(pageRequest);
	}
	
	@Override
	public Book save(Book book)
	{
		if (book.getId() != null && book.getId() != 0)
		{
			throw new InvalidIdState(messageSource.getCustomMessage("exception.InvalidIdState.idShouldBeNull"));
		}
		String imageName = book.getImageLink();
		if (null != imageName)
		{
			fileSystemStorageService.removeImageFromTracking(imageName);
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
	
	@Override
	public Page<Book> getPageByAnyTag(String tagString, PageRequest pageRequest)
	{
		List<String> tagList = processTags(tagString);
		return repository.findDistinctBooksByTagsInIgnoreCase(tagList, pageRequest);
	}
	
	@Override
	public Page<Book> getPageByAllTags(String tagString, PageRequest pageRequest)
	{
		List<String> tagList = processTags(tagString);
		return repository.findBooksMatchingAllTags(tagList, (long) tagList.size(), pageRequest);
	}
	
	private List<String> processTags(String tagString)
	{
		String[] tagArray = tagString.split(",");
		List<String> tagList = Arrays.asList(tagArray);
		return tagList.stream().map(String::toLowerCase).collect(Collectors.toList());
	}
}
