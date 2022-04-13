package org.siriusxi.htec.fa.api.model.upload.converter;

import com.opencsv.bean.processor.StringProcessor;

public class ConvertUnwantedStringsToDefault implements StringProcessor {
    
    String defaultValue;
    
    @Override
    public String processString(String value) {
        if (value == null || value.isBlank() ||
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
