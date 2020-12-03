package org.siriusxi.htec.fa.domain.dto.upload.converter;

import com.opencsv.bean.processor.StringProcessor;

public class ConvertUnwantedStringsToDefault implements StringProcessor {
    
    String defaultValue;
    
    @Override
    public String processString(String value) {
        if (value == null || value.trim().isEmpty() ||
                    value.equalsIgnoreCase("\\N") ||
                    value.equalsIgnoreCase("N")) {
            return defaultValue;
        }
        return value;
    }
    
    @Override
    public void setParameterString(String value) {
        defaultValue = value;
    }
}
