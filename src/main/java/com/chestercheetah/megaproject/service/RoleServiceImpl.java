package com.chestercheetah.megaproject.service;

import com.chestercheetah.megaproject.DAO.RoleDAO;
import com.chestercheetah.megaproject.entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleDAO dao;

    private static final Role [] rolesForUser = {new Role("USER"), new Role("USER")};

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

    @Override
    public Set<Role> convertRoleStringArrayToRoleSet(String[] selectedRoles) {
        Set<Role> rolesForNewUser = new LinkedHashSet<>();
        for (String role : selectedRoles) {
            rolesForNewUser.add(getRole(role));
        }
        if (rolesForNewUser.isEmpty()){
            rolesForNewUser.add(getRole("ROLE_USER"));
        }
        return rolesForNewUser;
    }
}
