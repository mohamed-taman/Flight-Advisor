package org.siriusxi.htec.fa.api.model.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public record CountryView(@JsonProperty int id,
                          @JsonProperty String name) {
}
