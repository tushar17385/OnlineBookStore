package com.bookStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.bookStore.entity.User;
import com.bookStore.repository.UserRepository;

@Service
public class UserService {


	
	@Autowired
	private UserRepository bRepo;

	

    private UserRepository userRepository1;


    @Autowired
    public UserService(UserRepository userRepository) {
		this.userRepository1 = userRepository;
    }

    public User getUserById(int id) {
        return userRepository1.findById(id).orElse(null);
    }

 
    public void registerNewUser(
        String firstname,
        String lastname,
        String email,
        String phoneno,
        String password,
        String role
    ) {
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPhoneno(phoneno);
        user.setPassword(password);
        user.setRole(role);
        userRepository1.save(user);
    }

    
    public List<User> getAlluser() {
        return bRepo.findAll();
    }
    
    public void deleteById(int id) {
		bRepo.deleteById(id);
	}

    
    public User loginUser(String username, String password) {
        User user = userRepository1.findByEmail(username);

        if (user != null && user.getPassword().equals(password)) {
            return user; 
        }
        return null;
    }


	
}

