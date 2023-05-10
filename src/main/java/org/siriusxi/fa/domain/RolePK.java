package org.siriusxi.fa.domain;

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

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class RolePK implements Serializable {


    @Serial
    private static final long serialVersionUID = 7751515991605761066L;

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
        return Objects.equals(this.userId, rolePK.userId)
                   && Objects.equals(this.authority, rolePK.authority);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.userId, this.authority);
    }
}
