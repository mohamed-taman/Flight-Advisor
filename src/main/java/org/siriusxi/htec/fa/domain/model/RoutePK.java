package org.siriusxi.htec.fa.domain.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class RoutePK implements Serializable {
    
    @NonNull
    @Basic(optional = false)
    @Column(name = "SOURCE_AIRPORT", nullable = false)
    private String source;
    
    @NonNull
    @Basic(optional = false)
    @Column(name = "DESTINATION_AIRPORT",nullable = false)
    private String destination;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        RoutePK routePK = (RoutePK) o;
        return Objects.equals(source, routePK.source)
                   && Objects.equals(destination, routePK.destination);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }
}
