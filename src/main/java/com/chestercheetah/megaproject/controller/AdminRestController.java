package com.chestercheetah.megaproject.controller;

import com.chestercheetah.megaproject.entity.User;
import com.chestercheetah.megaproject.entity.Views;
import com.chestercheetah.megaproject.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @JsonView(Views.userInfo.class)
    public List<User> userList () {
        return userService.getUserList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create (@RequestBody User user) {
        int id = userService.save(user);
        HttpStatus status = id > 0 ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(id, status);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update (@RequestBody User user, @PathVariable int id) {
        HttpStatus status = userService.update(user) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete (@PathVariable int id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
