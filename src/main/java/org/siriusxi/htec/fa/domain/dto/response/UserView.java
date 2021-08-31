package org.siriusxi.htec.fa.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserView(@JsonProperty String id,
                       @JsonProperty String username,
                       @JsonProperty String firstName,
                       @JsonProperty String lastName,
                       @JsonProperty String[] authorities) {
}
