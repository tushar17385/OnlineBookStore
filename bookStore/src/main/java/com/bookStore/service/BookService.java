package com.bookStore.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookStore.entity.Book;
import com.bookStore.entity.Cart;
import com.bookStore.entity.User;
import com.bookStore.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bRepo;
	

    private final BookRepository BookRepository;
   
	@Autowired
    public BookService(BookRepository BookRepository) {
        this.BookRepository = BookRepository;
        
    }

	
//	public void save(Book b) {
//		bRepo.save(b);
//	}
//	
	public List<Book> getAllBooks() {
	    List<Book> books = bRepo.findAll();
	    
	    for (Book book : books) {
	        byte[] coverData = book.getCover();
	        if (coverData != null) {
	            // Log the length of the cover data to see if it's populated
	            System.out.println("Cover Data Length for Book ID " + book.getId() + ": " + coverData.length);
	        }
	    }
	    
	    return books;
	}

	
	
	public void registerNewBook(String title, String author, String genre, String price,int quantity, MultipartFile cover) {
	    Book book = new Book();
	    book.setTitle(title);
	    book.setGenre(genre);
	    book.setAuthor(author);
	    book.setPrice(price);
	    book.setQuantity(quantity);

	    try {
	        book.setCover(cover.getBytes());
	    } catch (IOException e) {
	        e.printStackTrace();
	        // Handle the exception, e.g., by returning an error message to the user
	    }

	    BookRepository.save(book);
	}


	
	
	public Book getBookById(int id) {
		return bRepo.findById(id).get();
	}
	public void deleteById(int id) {
		bRepo.deleteById(id);
	}


	
}
