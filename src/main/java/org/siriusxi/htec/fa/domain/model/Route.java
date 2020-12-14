package org.siriusxi.htec.fa.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serial;
import java.io.Serializable;

import static javax.persistence.FetchType.*;

/**
 * @author Mohamed Taman
 * @version 1.0
 **/
@Entity
@Table(catalog = "FLIGHTDB", schema = "PUBLIC")
@Data
@NoArgsConstructor
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
    private Airport destinationAirport;
    
    @JoinColumn(name = "SOURCE_AIRPORT_ID", referencedColumnName = "AIRPORT_ID")
    @ManyToOne(fetch = LAZY)
    private Airport sourceAirport;
}