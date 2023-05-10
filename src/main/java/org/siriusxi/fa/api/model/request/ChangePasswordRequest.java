package org.siriusxi.fa.api.model.request;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(@NotBlank String newPassword,
                                    @NotBlank String newPasswordAgain) {
}
