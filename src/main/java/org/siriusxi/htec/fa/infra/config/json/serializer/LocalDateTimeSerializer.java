package org.siriusxi.htec.fa.infra.config.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    
    private static final DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE_TIME;
    
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen,
                          SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(fmt));
    }
}

