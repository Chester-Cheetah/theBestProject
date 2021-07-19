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
    public void save(User user) {
        if (dao.getUserByEmail(user.getEmail()) == null) {
            dao.save(user);
        }
    }

    @Override
    public User getUserByID(int id) {
        return dao.getUserByID(id);
    }

    @Override
    public void update(User user) {
        User existedUserWithTheSameEmail = dao.getUserByEmail(user.getEmail());
        if (existedUserWithTheSameEmail == null || existedUserWithTheSameEmail.getId() == user.getId()) {
            dao.update(user);
        }
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return dao.getUserByEmail(email);
    }
}
