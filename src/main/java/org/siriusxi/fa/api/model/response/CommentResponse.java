package org.siriusxi.fa.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonPropertyOrder({"id", "comment", "by", "createdAt", "updatedAt"})
public record CommentResponse(@JsonProperty int id,
                              @JsonProperty String comment,
                              @JsonProperty String by,
                              @JsonProperty("createdAt") LocalDateTime createdAt,
                              @JsonInclude(NON_NULL)
                          @JsonProperty("updatedAt") LocalDateTime updatedAt) {
}
