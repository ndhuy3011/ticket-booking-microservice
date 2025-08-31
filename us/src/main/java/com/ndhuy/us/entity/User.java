package com.ndhuy.us.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ndhuy.us.valueobject.Password;
import com.ndhuy.us.valueobject.Username;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "us_users")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "username", nullable = false, length = 50, unique = true))
    private Username username;

    @AttributeOverride(name = "value", column = @Column(name = "password", nullable = false, length = 50, unique = false))
    private Password password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<RoleAUser> roleAUsers;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (RoleAUser role : roleAUsers) {
            authorities.add(new SimpleGrantedAuthority(role.getId().getRole()));
        }
        return authorities;
    }

    public String getUsername() {
        return username.getUsername();
    }

    public String getPassword() {
        return password.getPassword();
    }
}
