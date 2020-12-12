package org.siriusxi.htec.fa.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public record TripView(@JsonProperty AirportView start,
                       @JsonInclude(NON_NULL)
                       @JsonProperty List<AirportView> through,
                       @JsonProperty AirportView end,
                       @JsonProperty double cost) {
}
