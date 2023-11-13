package com.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bookStore.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
