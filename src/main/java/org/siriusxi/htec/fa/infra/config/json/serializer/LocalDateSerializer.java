package org.siriusxi.htec.fa.infra.config.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {
    
    private static final DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE;
    
    @Override
    public void serialize(LocalDate value, JsonGenerator gen,
                          SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(fmt));
    }
}
