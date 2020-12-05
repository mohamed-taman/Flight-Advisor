package org.siriusxi.htec.fa.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

@JsonPropertyOrder({"id", "description", "created_by", "created_at", "updated_on"})
public record CommentView(@JsonProperty int id,
                          @JsonProperty String description,
                          @JsonProperty("created_by") String createdBy,
                          @JsonProperty("created_at") LocalDateTime createdAt,
                          @JsonProperty("updated_on") LocalDateTime updatedOn) {
}
