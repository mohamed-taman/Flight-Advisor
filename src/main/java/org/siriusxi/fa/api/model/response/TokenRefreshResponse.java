package org.siriusxi.fa.api.model.response;

public record TokenRefreshResponse(
        String accessToken,
        String refreshToken) {
}