package org.siriusxi.htec.fa.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthRequest(
    @JsonProperty("username") @Email @NotBlank String username,
    @JsonProperty("password") @NotBlank String password) {
}
