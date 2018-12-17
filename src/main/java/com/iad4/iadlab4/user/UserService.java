package com.iad4.iadlab4.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User addUser(User user) {
        return userRepository.saveAndFlush(user);
    }
    public User getOne(Long id) {
        return userRepository.getOne(id);
    }
}
