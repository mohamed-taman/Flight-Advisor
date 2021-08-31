package org.siriusxi.htec.fa.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public record SearchCityRequest(@JsonProperty("byName") String name) {
}
