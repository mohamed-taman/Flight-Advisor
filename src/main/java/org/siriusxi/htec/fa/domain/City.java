package org.siriusxi.htec.fa.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import static jakarta.persistence.GenerationType.IDENTITY;

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
    
    public City(Integer id) {
        this.id = id;
    }
    
    public City(String name, String description, Country country) {
        this(name, country);
        this.description = description;
    }
    
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
