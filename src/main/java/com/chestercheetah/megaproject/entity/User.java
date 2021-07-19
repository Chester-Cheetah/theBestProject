package com.chestercheetah.megaproject.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringJoiner;

@Entity
@Table (name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column (length = 50, nullable = false)
    private String firstName;

    @Column (length = 50, nullable = false)
    private String lastName;

    @Column
    private int age;

    @Column (length = 50, nullable = false, unique = true)
    private String email;

    @Column (length = 50, nullable = false)
    private String password;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable (name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new LinkedHashSet<>();


    public User() {}

    public User(String email, String password, String firstName, String lastName, int age) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getRolesString () {
        StringJoiner result = new StringJoiner(" ");
        roles.forEach(role -> result.add(role.getRoleNameWithoutPrefix()));
        return result.toString();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }



//    @Override
//    public String toString() {
//        return String.format("%s, пароль %s, email %s, %s, %s", email, password, firstName, lastName, age);
//    }
}
