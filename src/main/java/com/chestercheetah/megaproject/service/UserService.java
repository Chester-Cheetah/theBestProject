package com.chestercheetah.megaproject.service;

import com.chestercheetah.megaproject.entity.User;

import java.util.List;


public interface UserService {
    List<User> getUserList ();
    void save (User user);
    User getUserByEmail(String email);
    User getUserByID (int id);
    void update (User user);
    void delete (int id);
}
