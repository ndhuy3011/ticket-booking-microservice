package com.ndhuy.us.entity;

import java.util.Collection;

import com.ndhuy.us.valueobject.Description;
import com.ndhuy.us.valueobject.RoleName;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "us_roles")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Role implements java.io.Serializable {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "role", nullable = false, length = 50, unique = true))
    private RoleName roleName;

    @AttributeOverride(name = "value", column = @Column(name = "description", nullable = false, length = 50, unique = false))
    private transient Description roleDesc;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<RoleAUser> roleAUsers;

    public String getRoleName() {
        return roleName.getRoleName();
    }

}
