package org.siriusxi.htec.fa.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Mohamed Taman
 * @version 1.0
 **/
@Entity
@Table(name = "CITY_COMMENT", catalog = "FLIGHTDB", schema = "PUBLIC")
@Data
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
    private City city;
    
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    private User user;
    
    @Override
    public String toString() {
        return """
            Comment{id= %d, comment= %s, createdAt= %s, \
            updatedOn= %s, city= "%s", user= %s }"""
            .formatted(id, comment, createdAt, updatedAt, city.getName(), user.getUserUuid());
    }
}
