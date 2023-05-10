package org.siriusxi.fa.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;

/**
 * @author Mohamed Taman
 * @version 1.0
 **/
@Entity
@Table(name = "CITY_COMMENT", catalog = "FLIGHTDB", schema = "PUBLIC")
@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Comment implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 3792430609269167831L;
    
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
                   .formatted(this.id, this.comment, this.createdAt, this.updatedAt, this.city.getName(), this.user.getUserUuid());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        
        return Objects.equals(this.id, ((Comment) o).id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.comment, this.createdAt, this.updatedAt, this.city, this.user);
    }
}
