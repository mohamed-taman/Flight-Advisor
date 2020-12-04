package org.siriusxi.htec.fa;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.siriusxi.htec.fa.domain.dto.upload.airport.AirportDto;
import org.siriusxi.htec.fa.domain.dto.upload.airport.verifer.AirportBeanVerifier;
import org.siriusxi.htec.fa.domain.dto.upload.route.RouteDto;
import org.siriusxi.htec.fa.domain.dto.upload.route.verifer.RouteBeanVerifier;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;

@Log4j2
class CsvToBeanTests {
    
    @SneakyThrows
    @Test
    void parseRoutesCSVFileToBeans() {
        
        try (Reader fileReader = new FileReader("./src/test/resources/routes.txt")) {
            
            CsvToBean<RouteDto> csvToRouteBeans = new CsvToBeanBuilder<RouteDto>(fileReader)
                .withType(RouteDto.class)
                .withVerifier(new RouteBeanVerifier())
                .withIgnoreLeadingWhiteSpace(true)
                .build();
            
            // convert `CsvToBean` object to list of airports
            List<RouteDto> routes = csvToRouteBeans.parse();
            
            System.out.println(routes.size());
            
            Assertions.assertThat(routes.size()).isEqualTo(66754);
        }
    }
    
    @SneakyThrows
    @Test
    void parseAirportsCSVFileToBeans() {
        
        try (Reader fileReader = new FileReader("./src/test/resources/airports.txt")) {
            
            CsvToBean<AirportDto> csvToAirportBeans = new CsvToBeanBuilder<AirportDto>(fileReader)
                .withType(AirportDto.class)
                .withVerifier(new AirportBeanVerifier())
                .withIgnoreLeadingWhiteSpace(true)
                .build();
            
            // convert `CsvToBean` object to list of airports
            List<AirportDto> airports = csvToAirportBeans.parse();
            
            System.out.println(airports.size());
            
            Assertions.assertThat(airports.size()).isEqualTo(7140);
        }
    }
}
