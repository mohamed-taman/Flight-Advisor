package org.siriusxi.htec.fa.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserView(@JsonProperty("id") String id,
                       @JsonProperty("username") String username,
                       @JsonProperty("fullName") String fullName) {
}