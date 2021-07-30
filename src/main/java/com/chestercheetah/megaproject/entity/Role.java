package com.chestercheetah.megaproject.entity;


import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(Views.userInfo.class)
    private String roleName;

    public Role() {}

    public Role(String roleName) {
        this.roleName = roleName.startsWith("ROLE_") ? roleName.toUpperCase() : "ROLE_" + roleName.toUpperCase();
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRoleNameWithoutPrefix() {
        return roleName.startsWith("ROLE_") ? roleName.substring(5).toUpperCase() : roleName.toUpperCase();
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;
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
