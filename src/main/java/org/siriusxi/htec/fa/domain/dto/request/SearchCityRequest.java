package org.siriusxi.htec.fa.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record SearchCityRequest(@JsonProperty("name") @NotBlank String name) {
}
