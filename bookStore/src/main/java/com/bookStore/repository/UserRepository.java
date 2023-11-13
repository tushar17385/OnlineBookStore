package com.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bookStore.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	User findByEmail(String email);
	@Modifying
	@Query(value = "INSERT INTO users (first_name, last_name, email, phoneno, password, role) VALUES (:firstname, :lastname, :email, :phoneno, :password, :role)", nativeQuery = true)
	void insertUser(@Param("firstname") String firstname, @Param("lastname") String lastname, @Param("email") String email, @Param("phoneno") String phoneno, @Param("password") String password,@Param("role") String role);
}