package com.iad4.iadlab4.authentication;

import com.iad4.iadlab4.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CustomUserDetailsService implements UserDetailsService {
    private List<User> registeredUsers;

    public CustomUserDetailsService() {
        registeredUsers = new ArrayList<>();
    }

    private User findUser(String username) {
        for (User registeredUser : registeredUsers) {
            if (registeredUser.getUsername().equals(username)) {
                return registeredUser;
            }
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = findUser(s);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username: " + s + " not found");
        }
    }
}
