package com.chestercheetah.megaproject.entity;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;


@Entity
@Table (name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @Column (name = "roleName", unique = true)
    private String roleName;


    @Column (name = "roleNameRUS", unique = true)
    private String roleNameRUS;

    public Role() {}

    public Role(String roleName, String roleNameRUS) {
        this.roleName = roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;
        this.roleNameRUS = roleNameRUS;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRoleNameWithoutPrefix() {
        return roleName.startsWith("ROLE_") ? roleName.substring(5) : roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;
    }

    public String getRoleNameRUS() {
        return roleNameRUS;
    }

    public void setRoleNameRUS(String roleNameRUS) {
        this.roleNameRUS = roleNameRUS;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }

    @Override
    public String toString() {
        return getRoleNameWithoutPrefix();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(roleName, role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName);
    }
}
