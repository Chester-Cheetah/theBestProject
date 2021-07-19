package com.chestercheetah.megaproject.DAO;

import com.chestercheetah.megaproject.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDAO {

    @PersistenceContext
    private EntityManager manager;


    @Override
    public List<Role> roleList() {
        return manager.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public void add(Role role) {
        if (!manager.contains(role)) {
            manager.persist(role);
        }
    }

    @Override
    public Role getRole (String roleName){
        String role = roleName.startsWith("ROLE_") ? roleName.toUpperCase() : "ROLE_" + roleName.toUpperCase();
        return manager.find(Role.class, role);
    }
}
