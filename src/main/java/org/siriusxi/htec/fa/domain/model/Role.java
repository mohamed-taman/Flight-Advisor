package org.siriusxi.htec.fa.domain.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serial;

@Entity
@Table(name = "AUTHORITY", catalog = "FLIGHTDB", schema = "PUBLIC")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"rolePK"})
public class Role implements GrantedAuthority {
    
    public static final String CLIENT = "CLIENT";
    public static final String ADMIN = "ADMIN";
    
    @Serial
    private static final long serialVersionUID = 3353780708738749971L;
    
    @EmbeddedId
    protected RolePK rolePK;
    
    @NonNull
    @Basic(optional = false)
    @Column(nullable = false, insertable = false, updatable = false)
    private String authority;
    
    public Role(RolePK rolePK) {
        setRolePK(rolePK);
        this.authority = rolePK.getAuthority();
    }
}
