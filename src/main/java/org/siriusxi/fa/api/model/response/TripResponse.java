package org.siriusxi.fa.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public record TripResponse(
    @JsonProperty AirportResponse start,
    @JsonInclude(NON_NULL)
    @JsonProperty List<AirportResponse> through,
    @JsonProperty AirportResponse end,
    @JsonProperty Price price,
    @JsonProperty Distance distance) {
    
    public record Price(
        @JsonProperty double total,
        @JsonProperty String currency) {
    }
    
    public record Distance(
        @JsonProperty double total,
        @JsonProperty String in) {
    }
}
