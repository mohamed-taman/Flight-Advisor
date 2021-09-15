package org.siriusxi.htec.fa.domain.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;
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
@ToString(exclude = {"airports","cities"})
public class Country implements Serializable {
    
    public Country(Integer id) {
        this.id = id;
    }
    
    @Serial
    private static final long serialVersionUID = -9057344199173138205L;
    
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
    private List<Airport> airports;
    
    @OneToMany(cascade = ALL, mappedBy = "country", fetch = LAZY)
    @ToString.Exclude
    private List<City> cities;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, airports, cities);
    }
}
