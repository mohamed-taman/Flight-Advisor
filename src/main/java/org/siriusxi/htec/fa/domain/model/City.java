package org.siriusxi.htec.fa.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

/**
 * @author Mohamed Taman
 * @version 1.0
 **/
@Entity
@Table(catalog = "FLIGHTDB", schema = "PUBLIC")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class City implements Serializable {
    
    public City(Integer id) {
        this.id = id;
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
    private List<Comment> comments;
    
    @OneToMany(cascade = ALL, mappedBy = "city", fetch = LAZY)
    private List<Airport> airports;
    
    @NonNull
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    private Country country;
}
