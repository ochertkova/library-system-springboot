package com.rest_api.fs14backend.user;

import com.rest_api.fs14backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    public User addOne(User user) {
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User findById(UUID userId) {
        return userRepo.findById(userId).orElse(null);
    }

    public void deleteById(UUID userId) {
        if (userRepo.findById(userId).isPresent()) {
            userRepo.deleteById(userId);
        } else {
            throw new NotFoundException("User not found");
        }
    }
}
