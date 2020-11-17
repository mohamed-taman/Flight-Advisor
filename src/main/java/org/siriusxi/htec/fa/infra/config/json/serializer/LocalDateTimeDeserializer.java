package org.siriusxi.htec.fa.infra.config.json.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    
    private static final DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE_TIME;
    
    @Override
    public LocalDateTime deserialize(JsonParser p,
                                     DeserializationContext context) throws IOException {
        return LocalDateTime.parse(p.getValueAsString(), fmt);
    }
}
