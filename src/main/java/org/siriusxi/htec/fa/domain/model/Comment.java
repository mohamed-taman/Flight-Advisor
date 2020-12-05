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
import static javax.persistence.FetchType.*;

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
    
    @EmbeddedId
    protected CommentPK commentPK;
    
    @NonNull
    @Basic(optional = false)
    @Column(nullable = false, length = 1000)
    private String description;
    
    @Basic(optional = false)
    @Column(name = "CREATED_AT", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt = now();
    
    @Column(name = "UPDATED_ON")
    @LastModifiedDate
    private LocalDateTime updatedOn;
    
    @JoinColumn(
        name = "CITY_ID",
        referencedColumnName = "ID",
        nullable = false,
        insertable = false,
        updatable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    private City city;
    
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    private User user;
    
    public Comment(int cityId, String description) {
        this.setCommentPK(new CommentPK(cityId));
        this.description = description;
    }
    
    @Override
    public String toString() {
        return """
            Comment{description= "%s", createdAt= %s, \
            updatedOn= %s, city= "%s", user= %s }"""
            .formatted(description, createdAt, updatedOn,
                city.getName(), user.getUserUuid());
    }
}
