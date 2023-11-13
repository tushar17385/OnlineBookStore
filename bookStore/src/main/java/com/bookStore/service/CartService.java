package com.bookStore.service;

import com.bookStore.entity.Cart;
import com.bookStore.entity.User;
import com.bookStore.entity.Book;
import com.bookStore.repository.CartRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user);
    }


    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public List<Book> getCartBooks(User user) {
        Cart cart = getCartByUser(user);
        return cart != null ? cart.getBooks() : null;
    }

    public void addToCart(User user, Book book) {
        Cart cart = getCartByUser(user);

        if (cart == null) {
            cart = new Cart();
        }

        // Check if the book is not already in the cart, add it if it's not.
        if (!cart.getBooks().contains(book)) {
            cart.getBooks().add(book);
        }

        // Save the updated cart.
        saveCart(cart);
    }
    
    public void clearCart(Cart cart) {
        // Assuming Cart entity has a method to clear books
        cart.clearBooks(); // Implement this method in your Cart entity

        // Save the updated cart.
        saveCart(cart);
    }
    
    public void deleteByBookId(int id) {
        cartRepository.deleteById(id);
    }
}
