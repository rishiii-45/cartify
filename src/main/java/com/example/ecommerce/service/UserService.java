package com.example.ecommerce.service;

import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo){
        this.repo = repo;
    }

    public void register(User user){
        repo.save(user);
    }

    public User login(String email,String password){

        User user = repo.findByEmail(email);

        if(user != null && user.getPassword().equals(password)){
            return user;
        }

        return null;
    }

    public boolean emailExists(String email){
        return repo.findByEmail(email) != null;
    }
}