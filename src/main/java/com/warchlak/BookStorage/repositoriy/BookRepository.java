package com.warchlak.BookStorage.repositoriy;

import com.warchlak.BookStorage.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookRepository extends JpaRepository<Book, Integer>
{
}
