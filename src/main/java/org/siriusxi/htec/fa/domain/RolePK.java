package org.siriusxi.htec.fa.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class RolePK implements Serializable {
    
    @NonNull
    @Basic(optional = false)
    @Column(name = "USER_ID", nullable = false)
    private Integer userId;
    
    @NonNull
    @Basic(optional = false)
    @Column(nullable = false)
    private String authority;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        RolePK rolePK = (RolePK) o;
        return Objects.equals(userId, rolePK.userId)
                   && Objects.equals(authority, rolePK.authority);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(userId, authority);
    }
}
