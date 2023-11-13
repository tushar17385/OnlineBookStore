package com.bookStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.bookStore.entity.Order;
import com.bookStore.entity.User;
import com.bookStore.repository.BookRepository;
import com.bookStore.repository.CartRepository;
import com.bookStore.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartBookRepository;

    @Autowired
    private BookRepository bookRepository;
    
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }
  

   

    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }
    
}
