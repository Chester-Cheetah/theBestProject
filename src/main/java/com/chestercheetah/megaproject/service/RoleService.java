package com.chestercheetah.megaproject.service;

import com.chestercheetah.megaproject.entity.Role;

import java.util.List;

public interface RoleService {
    void add (Role role);
    List<Role> roleList();
    Role getRole (String role);
    void saveRoleListIfNotSaved();
}
