package org.siriusxi.htec.fa.domain.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.time.LocalDateTime.now;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Mohamed Taman
 * @version 1.0
 **/
@Entity
@Table(name = "CITY_COMMENT", catalog = "FLIGHTDB", schema = "PUBLIC")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Comment implements Serializable {
    
    @Serial
    private static final long serialVersionUID = -4628882357786781599L;
    
    @Id
    @NonNull
    @GeneratedValue(strategy = IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    
    @NonNull
    @Basic(optional = false)
    @Column(name = "DESCRIPTION", nullable = false, length = 1000)
    private String comment;
    
    @Basic(optional = false)
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt = now();
    
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
    
    @JoinColumn(name = "CITY_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    @ToString.Exclude
    private City city;
    
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    @ToString.Exclude
    private User user;
    
    @Override
    public String toString() {
        return """
            Comment{id= %d, comment= %s, createdAt= %s, \
            updatedOn= %s, city= "%s", user= %s }"""
            .formatted(id, comment, createdAt, updatedAt, city.getName(), user.getUserUuid());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        
        return Objects.equals(id, ((Comment) o).id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, comment, createdAt, updatedAt, city, user);
    }
}
