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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "us_users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {


    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "username", nullable = false, length = 50, unique = true))
    private Username userId;

    @AttributeOverride(name = "value", column = @Column(name = "password", nullable = false, length = 50, unique = false))
    private Password pwd;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<RoleAUser> roleAUsers;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (RoleAUser role : roleAUsers) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleAUserId().getRole()));
        }
        return authorities;
    }

    public void updatePassword(String newPassword) {
        this.pwd = Password.of(newPassword);

    }

    public String getUsername() {
        return userId.getValue();
    }

    public String getPassword() {
        return pwd.getValue();
    }

}
