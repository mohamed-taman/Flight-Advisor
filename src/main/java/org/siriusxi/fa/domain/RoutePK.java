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
public class RoutePK implements Serializable {

    @Serial
    private static final long serialVersionUID = -3409091883135162258L;
    
    @NonNull
    @Basic(optional = false)
    @Column(name = "SOURCE_AIRPORT", nullable = false)
    private String source;
    
    @NonNull
    @Basic(optional = false)
    @Column(name = "DESTINATION_AIRPORT", nullable = false)
    private String destination;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        RoutePK routePK = (RoutePK) o;
        return Objects.equals(this.source, routePK.source)
                   && Objects.equals(this.destination, routePK.destination);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.source, this.destination);
    }
}
