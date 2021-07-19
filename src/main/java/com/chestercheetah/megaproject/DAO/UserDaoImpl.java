package com.chestercheetah.megaproject.DAO;

import com.chestercheetah.megaproject.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDAO {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<User> getUserList() {
        return manager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void save(User user) {
        manager.persist(user);
    }

    @Override
    public User getUserByEmail(String email) {
        TypedQuery<User> query = manager.createQuery("select u from User u where u.email = :email", User.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public User getUserByID (int id) {
        return manager.find(User.class, id);
    }

    @Override
    public void update(User user) {
        manager.merge(user);
    }

    @Override
    public void delete(int id) {
        manager.remove(manager.find(User.class, id));
    }
}
