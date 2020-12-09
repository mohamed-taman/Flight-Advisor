package org.siriusxi.htec.fa.domain.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public record CountryView(@JsonProperty int id,
                          @JsonProperty String name) {
}
