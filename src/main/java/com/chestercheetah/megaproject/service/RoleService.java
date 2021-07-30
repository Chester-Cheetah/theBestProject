package com.chestercheetah.megaproject.service;

import com.chestercheetah.megaproject.entity.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    void add (Role role);
    List<Role> roleList();
    Role getRole (String role);
    void saveRoleListIfNotSaved();
    Set<Role> convertRoleStringArrayToRoleSet(String [] selectedRoles);
}
