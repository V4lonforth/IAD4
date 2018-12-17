package com.iad4.iadlab4.authentication;

import com.iad4.iadlab4.user.User;
import com.iad4.iadlab4.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {
    private TokenController tokenController;
    private final UserService userService;

    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 15;

    private static final int MIN_PASSWORD_LENGTH = 3;
    private static final int MAX_PASSWORD_LENGTH = 15;

    private static final int INPUT_ERROR = 422;

    @Autowired
    public AuthenticationController(UserService userService) {
        tokenController = new TokenController();
        this.userService = userService;
    }

    private String hashPassword(String password) {
        return Integer.toString(password.hashCode());
    }

    private String validateUsername(String username) {
        int length = username.length();
        if (length <= MIN_USERNAME_LENGTH) {
            return "Username length should be more than " + MIN_USERNAME_LENGTH + " symbols";
        }
        if (length >= MAX_USERNAME_LENGTH) {
            return "Username length should be less than " + MAX_USERNAME_LENGTH + " symbols";
        }
        if (!Character.isLetter(username.charAt(0))) {
            return "Username should start with letter";
        }
        return "";
    }
    private String validatePassword(String password) {
        int length = password.length();
        if (length <= MIN_PASSWORD_LENGTH) {
            return "Password length should be more than " + MIN_PASSWORD_LENGTH + " symbols";
        }
        if (length >= MAX_PASSWORD_LENGTH) {
            return "Password length should be less than " + MAX_PASSWORD_LENGTH + " symbols";
        }
        return "";
    }

    @RequestMapping("/register")
    public String register(@RequestParam(value="username") String username, @RequestParam(value="password") String password, HttpServletResponse response) {
        if (userService.findByUsername(username) != null) {
            response.setStatus(INPUT_ERROR);
            return "User with given username exists";
        }
        String err = validateUsername(username);
        if (!err.equals("")) {
            response.setStatus(INPUT_ERROR);
            return err;
        }
        err = validatePassword(password);
        if (!err.equals("")) {
            response.setStatus(INPUT_ERROR);
            return err;
        }
        User user = new User(username, password);
        user = userService.addUser(user);
        return tokenController.createToken(user);
    }
    @RequestMapping("/login")
    public String login(@RequestParam(value="username") String username, @RequestParam(value="password") String password, HttpServletResponse response) {
        User user = userService.findByUsername(username);
        String message;
        if (user != null) {
            if (BCrypt.checkpw(password, user.getPassword())) {
                return tokenController.createToken(user);
            } else {
                message = "Incorrect password";
            }
        } else {
            message = "User with given name doesn't exist";
        }
        response.setStatus(INPUT_ERROR);
        return message;
    }
}
