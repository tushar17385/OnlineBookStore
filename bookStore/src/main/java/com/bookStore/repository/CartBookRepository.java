package com.bookStore.repository;

import com.bookStore.entity.CartBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartBookRepository extends JpaRepository<CartBook, Long> {
    // Your custom queries if needed
}
