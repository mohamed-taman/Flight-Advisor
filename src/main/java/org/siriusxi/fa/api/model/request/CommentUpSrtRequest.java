package org.siriusxi.fa.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("CommentRequest")
public record CommentUpSrtRequest(@JsonProperty("comment") @NotBlank String comment) {
}
