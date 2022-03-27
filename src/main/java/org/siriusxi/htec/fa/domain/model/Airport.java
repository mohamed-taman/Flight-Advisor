package org.siriusxi.htec.fa.domain.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

/**
 * @author Mohamed Taman
 * @version 1.0
 **/
@Entity
@Table(catalog = "FLIGHTDB", schema = "PUBLIC")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Airport implements Serializable {
    
    public enum Dst {
        E, A, S, O, Z, N, U
    }
    
    public Airport(Integer airportId) {
        this.airportId = airportId;
    }
    
    @Serial
    private static final long serialVersionUID = 6913599167936010779L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "AIRPORT_ID", nullable = false)
    private Integer airportId;
    
    @NonNull
    @Basic(optional = false)
    @Column(nullable = false)
    private String name;
    
    @NonNull
    @Basic(optional = false)
    @Column(name = "CITY", nullable = false, length = 100)
    private String cityName;
    
    @NonNull
    @Basic(optional = false)
    @Column(name = "COUNTRY", nullable = false, length = 100)
    private String countryName;
    
    @Column(length = 3)
    private String iata;
    
    @Column(length = 4)
    private String icao;
    
    @NonNull
    @Basic(optional = false)
    @Column(nullable = false, precision = 12, scale = 6)
    private BigDecimal latitude;
    
    @NonNull
    @Basic(optional = false)
    @Column(nullable = false, precision = 12, scale = 6)
    private BigDecimal longitude;
    
    @NonNull
    private Integer altitude;
    
    @NonNull
    private Float timezone;
    
    @NonNull
    @Enumerated(STRING)
    @Basic(optional = false)
    @Column(nullable = false)
    private Dst dst;
    
    @NonNull
    @Column(length = 50)
    private String tz;
    
    @NonNull
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String type;
    
    @NonNull
    @Basic(optional = false)
    @Column(name = "DATA_SOURCE", nullable = false)
    private String dataSource;
    
    @OneToMany(mappedBy = "destinationAirport", fetch = LAZY)
    @ToString.Exclude
    private List<Route> destinationRoutes;
    
    @OneToMany(mappedBy = "sourceAirport", fetch = LAZY)
    @ToString.Exclude
    private List<Route> sourceRoutes;
    
    @JoinColumn(name = "CITY_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    @ToString.Exclude
    private City city;
    
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    @ToString.Exclude
    private Country country;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
     
        return Objects.equals(airportId, ((Airport) o).airportId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, altitude);
    }
}
