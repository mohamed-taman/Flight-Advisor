package org.siriusxi.htec.fa.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record AuthRequest(@JsonProperty("username") @Email @NotBlank String username,
                          @JsonProperty("password") @NotBlank String password) { }
