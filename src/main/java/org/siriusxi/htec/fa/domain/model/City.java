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
import static javax.persistence.GenerationType.IDENTITY;

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
public class City implements Serializable {
    
    public City(Integer id) {
        this.id = id;
    }
    
    public City(String name, String description, Country country) {
        this(name, country);
        this.description = description;
    }
    
    @Serial
    private static final long serialVersionUID = 1322727266984495327L;
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    
    @NonNull
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String name;
    
    @Basic(optional = false)
    @Column(length = 100)
    private String description;
    
    @OneToMany(cascade = ALL, mappedBy = "city", fetch = LAZY)
    @ToString.Exclude
    private List<Comment> comments;
    
    @OneToMany(cascade = ALL, mappedBy = "city", fetch = LAZY)
    @ToString.Exclude
    private List<Airport> airports;
    
    @NonNull
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    @ToString.Exclude
    private Country country;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
      
        return Objects.equals(id, ((City) o).id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, comments, airports, country);
    }
}
