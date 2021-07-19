package com.chestercheetah.megaproject.service;

import com.chestercheetah.megaproject.DAO.UserDAO;
import com.chestercheetah.megaproject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service ("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDAO dao;

    @Autowired
    public UserDetailsServiceImpl(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = dao.getUserByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с таким email не найден");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }
}
