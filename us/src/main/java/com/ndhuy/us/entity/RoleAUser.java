package com.ndhuy.us.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.ndhuy.us.valueobject.RoleName;
import com.ndhuy.us.valueobject.Username;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "us_role_a_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleAUser extends BaseEntity  {
    @EmbeddedId
    private RoleAUserId roleAUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("username")
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("role")
    @JoinColumn(name = "role", referencedColumnName = "role")
    private Role role;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    private Date createDate;

    public static RoleAUser builder(User user, Role role) {
        var roleAUser = new RoleAUser();
        roleAUser.setUser(user);
        roleAUser.setRole(role);
        roleAUser.setRoleAUserId(new RoleAUserId(user.getUserId(), role.getRoleId()));
        return roleAUser;
    }

    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class RoleAUserId implements Serializable {
        private static final long serialVersionUID = 1L;
        private Username username;
        private RoleName role;

        public String getRole() {
            return role.getValue();
        }

        public String getUsername() {
            return username.getValue();
        }

        public static RoleAUserId builder(Username username, RoleName role) {
            return new RoleAUserId(username, role);
        }
    }
}
