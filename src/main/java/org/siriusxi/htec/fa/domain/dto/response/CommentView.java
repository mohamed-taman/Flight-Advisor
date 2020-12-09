package org.siriusxi.htec.fa.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonPropertyOrder({"id", "city", "comment", "by", "created_at", "updated_at"})
public record CommentView(@JsonProperty int id,
                          @JsonProperty String city,
                          @JsonProperty String comment,
                          @JsonProperty String by,
                          @JsonProperty("created_at") LocalDateTime createdAt,
                          @JsonInclude(NON_NULL)
                          @JsonProperty("updated_at") LocalDateTime updatedAt) {
}