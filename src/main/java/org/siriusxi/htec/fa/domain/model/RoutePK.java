package org.siriusxi.htec.fa.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
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
    
}
