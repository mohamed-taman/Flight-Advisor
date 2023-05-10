package org.siriusxi.fa;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.siriusxi.fa.api.model.upload.airport.AirportDto;
import org.siriusxi.fa.api.model.upload.airport.verifer.AirportBeanVerifier;
import org.siriusxi.fa.api.model.upload.route.RouteDto;
import org.siriusxi.fa.api.model.upload.route.verifer.RouteBeanVerifier;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Log4j2
class CsvToBeanTests {
    
    //@Test
    void parseRoutesCSVFileToBeans() {

        try (Reader fileReader = new FileReader("./data/routes.txt")) {

            com.opencsv.bean.CsvToBean<RouteDto> csvToRouteBeans = new CsvToBeanBuilder<RouteDto>(fileReader)
                                                      .withType(RouteDto.class)
                                                      .withVerifier(new RouteBeanVerifier())
                                                      .withIgnoreLeadingWhiteSpace(true)
                                                      .build();

            // convert `CsvToBean` object to list of airports
            List<RouteDto> routes = csvToRouteBeans.parse();

            System.out.println(routes.size());

            Assertions.assertThat(routes).hasSize(66754);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // @Test
    void parseAirportsCSVFileToBeans() throws FileNotFoundException {

        try (Reader fileReader = new FileReader("./data/airports.txt")) {

            com.opencsv.bean.CsvToBean<AirportDto> csvToAirportBeans = new CsvToBeanBuilder<AirportDto>(fileReader)
                                                          .withType(AirportDto.class)
                                                          .withVerifier(new AirportBeanVerifier())
                                                          .withIgnoreLeadingWhiteSpace(true)
                                                          .build();

            // convert `CsvToBean` object to list of airports
            List<AirportDto> airports = csvToAirportBeans.parse();

            System.out.println(airports.size());

            Assertions.assertThat(airports).hasSize(7140);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
