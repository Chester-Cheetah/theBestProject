package com.chestercheetah.megaproject.service;

import com.chestercheetah.megaproject.entity.User;

import java.util.List;


public interface UserService {
    List<User> getUserList ();
    boolean save (User user);
    User getUserByName (String name);
    User getUserByID (int id);
    boolean update (User user);
    void delete (int id);
}
