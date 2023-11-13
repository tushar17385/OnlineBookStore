package com.bookStore.entity;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

      

    
    @Column(name = "cart_date")
    private LocalDateTime additionDate;

    public LocalDateTime getAdditionDate() {
        return additionDate != null ? additionDate : LocalDateTime.now();
    }

    public void setAdditionDate(LocalDateTime additionDate) {
        this.additionDate = additionDate;
    }

    @ManyToMany
    @JoinTable(name = "cart_books",
        joinColumns = @JoinColumn(name = "cart_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;
    
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;



    @Column(nullable = true)
    private String title;

    public Cart() {
        books = new ArrayList<>();
    }

    // Getter and setter for 'title'
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    // Method to add a book to the cart
    public void addBook(Book book) {
        books.add(book);
    }

    public int getQuantity() {
        // Assuming books is not null and not empty
        return books.get(0).getQuantity();
    }

    
    // Other getters and setters for other fields

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void clearBooks() {
        books.clear();
    }
  
}
