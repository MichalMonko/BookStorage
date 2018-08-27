package com.warchlak.BookStorage.repository;

import com.warchlak.BookStorage.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer>
{
	List<Book> findAllByTitleIgnoreCase(String title);
	
	Page<Book> findDistinctBooksByTagsInIgnoreCase(List<String> requestedTags, Pageable pageable);
}
