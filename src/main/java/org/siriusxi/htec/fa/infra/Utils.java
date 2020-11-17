package org.siriusxi.htec.fa.infra;

import java.util.UUID;

public final class Utils {
    
    private Utils() {
    }
    
    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }
}
