package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/users" )
public class UserController {

    private final UserDao userDao;
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping()
    public User[] getAllUsers() {
        List<User> users = userDao.findAll();
        return users.toArray(new User[0]);
    }

    @GetMapping(path = "/{id}")
    public User getUser(@PathVariable int id) {
        User user = userDao.getUserById(id);
        return user;
    }
}