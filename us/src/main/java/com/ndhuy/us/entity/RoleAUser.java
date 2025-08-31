package com.ndhuy.us.entity;

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
public class RoleAUser implements java.io.Serializable {
    @EmbeddedId
    private RoleAUserId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("username")
    @JoinColumn(name = "username", referencedColumnName = "value")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("role")
    @JoinColumn(name = "role", referencedColumnName = "roleName")
    private Role role;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    private Date createDate;

    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    public class RoleAUserId implements java.io.Serializable {
        private Username username;
        private RoleName role;

        public String getRole() {
            return role.getRoleName();
        }

        public String getUsername() {
            return username.getUsername();
        }
    }
}
