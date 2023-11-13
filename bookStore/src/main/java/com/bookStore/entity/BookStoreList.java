package com.bookStore.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class BookStoreList {
    

	@Id
    private int id;
    private String name;
    private String author;
    private String price;
    private Date date;
    private String genre;
    private int quantity;
    private String publisher;
    public BookStoreList() {
    	super();
        // Default constructor
    }

    public BookStoreList(int book_id, String author_name, Date publish_date, String title, String genre, int stock_quantity, String publisher_name, String price) {
    	super();
    	this.id = book_id;
        this.name = title;
        this.author = author_name;
        this.price = price;
        this.date = publish_date;
        this.genre = genre;
        this.quantity = stock_quantity;
        this.publisher = publisher_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
