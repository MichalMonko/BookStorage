package com.warchlak.BookStorage.repository;

import com.warchlak.BookStorage.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public interface BookRepository extends JpaRepository<Book, Integer>
{
	List<Book> findAllByTitleIgnoreCase(String title);
}
