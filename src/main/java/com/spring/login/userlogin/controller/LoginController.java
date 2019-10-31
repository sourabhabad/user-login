package com.spring.login.userlogin.controller;

import com.spring.login.userlogin.model.Login;
import com.spring.login.userlogin.model.User;
import com.spring.login.userlogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/registration")
    public ResponseEntity<User> createNewUser(@Valid @RequestBody User user) {
        User userExists = userRepository.findByEmail(user.getEmail());
        if (userExists != null) {
            return new ResponseEntity<>(userExists, HttpStatus.EXPECTATION_FAILED);
        } else {
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody Login login) {
        User userExists = userRepository.findByEmail(login.getEmail());
        if (userExists != null && (userExists.getPassword()).equals(login.getPassword())) {
            if ((userExists.getPassword()).equals(login.getPassword())) {
                userExists.setActive(1);
                userRepository.save(userExists);
                return new ResponseEntity<>(userExists, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/logout/{email}")
    public ResponseEntity logout(@PathVariable("email") String email) {
        User userExists = userRepository.findByEmail(email);
        if (userExists != null && userExists.getActive() == 1) {
            userExists.setActive(0);
            userRepository.save(userExists);
            return new ResponseEntity<>(HttpStatus.OK);
        } else if (userExists != null && userExists.getActive() == 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<List<User>> getAllLoggedInUser() {
        List<User> userList = new ArrayList<>();
        List<User> allUser = userRepository.findAll();
        for (User user : allUser) {
            if (user.getActive() == 1) {
                userList.add(user);
            }
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}