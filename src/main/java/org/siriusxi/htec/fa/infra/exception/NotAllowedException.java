package org.siriusxi.htec.fa.infra.exception;

import static java.lang.String.*;

public class NotAllowedException extends RuntimeException {
    
    public NotAllowedException(String message) {
        super(message);
    }
    
    public NotAllowedException(Class<?> clazz, long id, String method) {
        super(getFormattedMessage(valueOf(id), method, clazz.getSimpleName()));
    }
    
    public NotAllowedException(Class<?> clazz, String id, String method) {
        super(getFormattedMessage(id, method, clazz.getSimpleName()));
    }
    
    private static String getFormattedMessage(String id, String method, String entity) {
        return format("You are not allowed to [%s] %s [id = %s] which is not yours.",
            method, entity, id );
    }
    
    
}
