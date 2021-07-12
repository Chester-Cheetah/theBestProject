package com.chestercheetah.megaproject.DAO;

import com.chestercheetah.megaproject.entity.Role;

import java.util.List;

public interface RoleDAO {

    void add(Role role);
    List<Role> roleList();
    Role getRole (String role);
}
