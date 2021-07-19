package com.chestercheetah.megaproject.service;

import com.chestercheetah.megaproject.DAO.RoleDAO;
import com.chestercheetah.megaproject.entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleDAO dao;

    private static final List <Role> rolesForUser = new ArrayList<>();

    static {
        String [] roleNamesENG = {"USER", "ADMIN"};
        String [] roleNamesRUS = {"Юзер", "Админ"};
        for (int i = 0; i < roleNamesENG.length; i++) {
            rolesForUser.add(new Role(roleNamesENG[i], roleNamesRUS[i]));
        }
    }

    public RoleServiceImpl(RoleDAO dao) {
        this.dao = dao;
    }

    @Override
    public void add(Role role) {
        dao.add(role);
    }

    @Override
    public List<Role> roleList() {
        return dao.roleList();
    }

    @Override
    public Role getRole(String role) {
        return dao.getRole(role);
    }

    @Override
    public void saveRoleListIfNotSaved() {
        for (Role role : rolesForUser) {
            try {
                dao.add(role);
            } catch (Exception ignored) {}
        }
    }
}
