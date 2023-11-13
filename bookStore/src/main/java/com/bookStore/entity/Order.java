package com.bookStore.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinTable(name = "details",
        joinColumns = @JoinColumn(name = "id"),
        inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;
    
    @ManyToOne
    @JoinColumn(name = "ordered_book_id")  // Use a distinct name for this column
    private Book orderedBook;

  
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "quantity")
    private int quantity;
    
    @Column(name = "order_date")
    private LocalDateTime order_date;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderBook> orderBooks = new ArrayList<>();

    // Constructors, getters, and setters...

    @Column(name = "total_price")
    private double total_Price;

    public Book getOrderedBook() {
        return orderedBook;
    }

    public void setOrderedBook(Book orderedBook) {
        this.orderedBook = orderedBook;
    }
    
    public double getTotal_Price() {
        return total_Price;
    }
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setTotal_Price(double total_Price) {
        this.total_Price = total_Price;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    
   

    
    public Cart getCartId() {
        return cart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDateTime localDateTime) {
        this.order_date = localDateTime;
    }

    public List<OrderBook> getOrderBooks() {
        return orderBooks;
    }

    public void setOrderBooks(List<OrderBook> orderBooks) {
        this.orderBooks = orderBooks;
    }

    public void addOrderBook(OrderBook orderBook) {
        this.orderBooks.add(orderBook);
        orderBook.setOrder(this);
    }

	public void setCartId(Cart cartid) {
		this.cart = cartid;
		
	}

	
}
