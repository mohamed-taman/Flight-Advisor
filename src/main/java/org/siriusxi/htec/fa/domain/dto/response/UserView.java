package org.siriusxi.htec.fa.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "username", "full_name"})
public record UserView(@JsonProperty String id,
                       @JsonProperty String username,
                       @JsonProperty("full_name") String fullName) {
}
