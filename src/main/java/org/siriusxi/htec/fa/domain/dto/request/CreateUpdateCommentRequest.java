package org.siriusxi.htec.fa.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record CreateUpdateCommentRequest(@JsonProperty("description") @NotBlank String description) {
}
