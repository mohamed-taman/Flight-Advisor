package org.siriusxi.fa.infra.exception;

import java.io.Serial;

import static java.lang.String.format;
import static java.lang.String.valueOf;

public class NotFoundException extends RuntimeException {


    @Serial
    private static final long serialVersionUID = -2031412312708381239L;

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
        return format("%s with id %s not found", entity, id);
    }
}
