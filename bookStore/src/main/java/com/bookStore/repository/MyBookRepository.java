package com.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookStore.entity.BookStoreList;


@Repository
public interface MyBookRepository extends JpaRepository<BookStoreList,Integer>{

}
