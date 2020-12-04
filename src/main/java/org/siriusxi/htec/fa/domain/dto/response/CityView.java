package org.siriusxi.htec.fa.domain.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CityView(@JsonProperty int id,
                       @JsonProperty String name,
                       @JsonProperty String country,
                       @JsonProperty String description,
                       @JsonProperty List<CommentView> comments) {
}
