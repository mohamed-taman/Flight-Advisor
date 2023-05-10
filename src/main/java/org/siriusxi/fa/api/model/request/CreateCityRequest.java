package org.siriusxi.fa.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"name", "country", "countryId", "description"})
public record CreateCityRequest(
    @JsonProperty("name") @NotBlank @Size(min = 5, max = 100) String name,
    @JsonProperty("country") @Size(min = 5, max = 100) String country,
    @JsonProperty("countryId") @PositiveOrZero int countryId,
    @JsonProperty("description") @NotBlank String description) {
}

