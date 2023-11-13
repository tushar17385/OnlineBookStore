package com.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bookStore.entity.Cart;
import com.bookStore.entity.User;


public interface CartRepository extends JpaRepository<Cart, Integer> {
    // Custom query method to find a cart by the user
    Cart findByUser(User user);
}