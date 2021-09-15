package org.siriusxi.htec.fa.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public record AirportView(@JsonInclude(NON_NULL)
                          @JsonProperty Integer id,
                          @JsonProperty String airport,
                          @JsonProperty String city,
                          @JsonProperty String country,
                          @JsonInclude(NON_NULL)
                          @JsonProperty String iata,
                          @JsonInclude(NON_NULL)
                          @JsonProperty String icao) {
}



