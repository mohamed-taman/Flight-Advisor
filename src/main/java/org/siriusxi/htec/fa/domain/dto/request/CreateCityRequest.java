package org.siriusxi.htec.fa.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonPropertyOrder({"name", "country", "country_id", "description"})
public record CreateCityRequest(
    @JsonProperty("name") @NotBlank @Size(min = 5, max = 100) String name,
    @JsonProperty("country") @Size(min = 5, max = 100) String country,
    @JsonProperty("country_id") @PositiveOrZero int countryId,
    @JsonProperty("description") @NotBlank String description) {
}

