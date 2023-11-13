package com.bookStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookStore.entity.Order;
import com.bookStore.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query("SELECT o FROM Order o WHERE o.user = :user ORDER BY o.order_date DESC")
	List<Order> findByUserOrderByOrderDateDesc(@Param("user") User user);
}
