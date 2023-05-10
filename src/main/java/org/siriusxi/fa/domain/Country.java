package org.siriusxi.fa.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.ALL;
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
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Country implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 7665467979286278572L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;

    @NonNull
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(cascade = ALL, mappedBy = "country", fetch = LAZY)
    @ToString.Exclude
    private transient List<Airport> airports;

    @OneToMany(cascade = ALL, mappedBy = "country", fetch = LAZY)
    @ToString.Exclude
    private transient List<City> cities;
    
    public Country(Integer id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Country country = (Country) o;
        return Objects.equals(this.id, country.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.airports, this.cities);
    }
}
