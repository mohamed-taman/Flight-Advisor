package org.siriusxi.htec.fa.infra.exception;

import static java.lang.String.*;

public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String message) {
        super(message);
    }
    
    public NotFoundException(Class<?> clazz, long id) {
        super(getFormattedMessage(clazz.getSimpleName(), valueOf(id)));
    }
    
    public NotFoundException(Class<?> clazz, String id) {
        super(getFormattedMessage(clazz.getSimpleName(), id));
    }
    
    private static String getFormattedMessage(String entity, String id) {
        return format("%s with id %s not found", entity, id );
    }
}
