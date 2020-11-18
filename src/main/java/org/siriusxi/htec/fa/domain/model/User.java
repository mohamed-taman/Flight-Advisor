package org.siriusxi.htec.fa.domain.model;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

/**
 * Class represents User domain model, as JPA Entity.
 *
 * @author Mohamed Taman
 * @version 1.0
 */
@Entity
@Table(
        catalog = "FLIGHTDB",
        schema = "PUBLIC",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"USER_UUID"}),
                @UniqueConstraint(columnNames = {"USERNAME"})
        })
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"userUuid", "username", "password", "comments", "enabled"})
public class User implements UserDetails, Serializable {
    
    @Serial
    private static final long serialVersionUID = 5666668516577592568L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "USER_UUID", nullable = false, updatable = false)
    private String userUuid;
    
    @NonNull
    @Basic(optional = false)
    @Column(name = "FIRST_NAME", nullable = false, length = 100)
    private String firstName;
    
    @NonNull
    @Basic(optional = false)
    @Column(name = "LAST_NAME", nullable = false, length = 100)
    private String lastName;
    
    @NonNull
    @Basic(optional = false)
    @Column(nullable = false)
    private String username;
    
    @NonNull
    @Basic(optional = false)
    @Column(nullable = false)
    private String password;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = LAZY)
    private List<Comment> comments;
    
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID",
            nullable = false, insertable = false, updatable = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = EAGER)
    private Set<Role> authorities = new HashSet<>();
    
    private boolean enabled = true;
    
    public void setAuthorities(Set<Role> roles) {
        for (Role role : roles) {
            role.setRolePK(new RolePK(this.getId(), role.getAuthority()));
            this.authorities.add(role);
        }
    }
    
    public void setAuthorities(String... authorities) {
        for (String authority : authorities)
            this.authorities.add(new Role(new RolePK(this.getId(), authority)));
    }
    
    public String getFullName() {
        return getFirstName()
                       .concat(" ")
                       .concat(getLastName());
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }
}
