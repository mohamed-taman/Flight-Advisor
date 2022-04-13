package org.siriusxi.htec.fa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;

/**
 * @author Mohamed Taman
 * @version 1.0
 **/
@Entity
@Table(catalog = "FLIGHTDB", schema = "PUBLIC")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Route implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 8818845493466966108L;
    
    @EmbeddedId
    protected RoutePK routePK;
    
    @Column(name = "AIRLINE_CODE", length = 3)
    private String airlineCode;
    
    @Column(name = "AIRLINE_ID")
    private Integer airlineId;
    
    @Column(name = "CODE_SHARE")
    private Boolean codeShare;
    
    private Integer stops;
    
    @Column(length = 100)
    private String equipment;
    
    @Max(value = 99999)
    @Min(value = 5)
    @Column(precision = 6, scale = 3)
    private double price;
    
    @JoinColumn(name = "DESTINATION_AIRPORT_ID", referencedColumnName = "AIRPORT_ID")
    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private Airport destinationAirport;
    
    @JoinColumn(name = "SOURCE_AIRPORT_ID", referencedColumnName = "AIRPORT_ID")
    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private Airport sourceAirport;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Route route = (Route) o;
        return Objects.equals(routePK, route.routePK);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(routePK);
    }
}