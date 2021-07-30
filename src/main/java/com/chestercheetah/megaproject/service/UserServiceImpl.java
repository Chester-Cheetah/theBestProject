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
    public int save(User user) {
        if (dao.getUserByEmail(user.getEmail()) == null) {
            return dao.save(user);
        }
        return -1;
    }

    @Override
    public User getUserByID(int id) {
        return dao.getUserByID(id);
    }

    @Override
    public boolean update(User user) {
        User existedUserWithTheSameEmail = dao.getUserByEmail(user.getEmail());
        boolean result = existedUserWithTheSameEmail == null || existedUserWithTheSameEmail.getId() == user.getId();
        if (result) dao.update(user);
        return result;
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
