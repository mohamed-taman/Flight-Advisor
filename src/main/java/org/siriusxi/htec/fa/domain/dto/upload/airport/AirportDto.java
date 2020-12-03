package org.siriusxi.htec.fa.domain.dto.upload.airport;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.processor.PreAssignmentProcessor;
import lombok.Data;
import org.siriusxi.htec.fa.domain.dto.upload.converter.ConvertUnwantedStringsToDefault;

import java.math.BigDecimal;

/**
 * @author Mohamed Taman
 * @version 1.0
 **/
@Data
public class AirportDto {
    
    @PreAssignmentProcessor(processor = ConvertUnwantedStringsToDefault.class, paramString = "0")
    @CsvBindByPosition(position = 0)
    private int airportId;
    
    @CsvBindByPosition(position = 1)
    private String name;
    
    @CsvBindByPosition(position = 2)
    private String city;
    
    @CsvBindByPosition(position = 3)
    private String country;
    
    @PreAssignmentProcessor(processor = ConvertUnwantedStringsToDefault.class, paramString = "")
    @CsvBindByPosition(position = 4)
    private String iata;
    
    @PreAssignmentProcessor(processor = ConvertUnwantedStringsToDefault.class, paramString = "")
    @CsvBindByPosition(position = 5)
    private String icao;
    
    @CsvBindByPosition(position = 6)
    private BigDecimal latitude;
    
    @CsvBindByPosition(position = 7)
    private BigDecimal longitude;
    
    @CsvBindByPosition(position = 8)
    private short altitude;
    
    @PreAssignmentProcessor(processor = ConvertUnwantedStringsToDefault.class,
            paramString = "-99.9")
    @CsvBindByPosition(position = 9)
    private float timezone;
    
    @PreAssignmentProcessor(processor = ConvertUnwantedStringsToDefault.class, paramString = "U")
    @CsvBindByPosition(position = 10)
    private String dst;
    
    @PreAssignmentProcessor(processor = ConvertUnwantedStringsToDefault.class, paramString = "U")
    @CsvBindByPosition(position = 11)
    private String tz;
    
    @CsvBindByPosition(position = 12)
    private String type;
    
    @CsvBindByPosition(position = 13)
    private String dataSource;
    
    // Used for Airport Model mapping of country and city
    private int cityId;
    private int countryId;
}
