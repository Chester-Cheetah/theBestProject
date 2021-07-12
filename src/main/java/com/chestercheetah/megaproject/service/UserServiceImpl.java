package com.chestercheetah.megaproject.service;

import com.chestercheetah.megaproject.DAO.UserDAO;
import com.chestercheetah.megaproject.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDAO dao;

    public UserServiceImpl(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<User> getUserList() {
        return dao.getUserList();
    }

    @Override
    public boolean save(User user) {
        if ((dao.getUserByEmail(user.getEmail()) == null) && (dao.getUserByName(user.getUsername()) == null)) {
            dao.save(user);
            return true;
        }
        return false;
    }


    @Override
    public User getUserByID(int id) {
        return dao.getUserByID(id);
    }

    @Override
    public boolean update(User user) {
        User anotherUserWithTheSameEmail = dao.getUserByEmail(user.getEmail());
        User anotherUserWithTheSameUsername = dao.getUserByName(user.getUsername());
        if ((anotherUserWithTheSameEmail == null || anotherUserWithTheSameEmail.getId() == user.getId())
                && (anotherUserWithTheSameUsername == null || anotherUserWithTheSameUsername.getId() == user.getId())) {
            dao.update(user);
            return true;
        }
        return false;
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }



    @Override
    public User getUserByName (String name) {
        return dao.getUserByName(name);
    }
}
