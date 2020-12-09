package org.siriusxi.htec.fa.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown=true)
public record UpSrtCommentRequest(
    @JsonProperty("comment") @NotBlank String comment) {
}