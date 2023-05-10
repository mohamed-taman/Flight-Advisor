package org.siriusxi.fa.infra.exception;

import java.io.Serial;

public class RefreshTokenException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8896944115746520398L;

    public RefreshTokenException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}
