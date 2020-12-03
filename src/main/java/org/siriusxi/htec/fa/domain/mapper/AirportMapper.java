package org.siriusxi.htec.fa.domain.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.siriusxi.htec.fa.domain.dto.upload.airport.AirportDto;
import org.siriusxi.htec.fa.domain.model.Airport;
import org.siriusxi.htec.fa.domain.model.City;
import org.siriusxi.htec.fa.domain.model.Country;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    
    @Mapping(target = "destinationRoutes", ignore = true)
    @Mapping(target = "sourceRoutes", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(source = "city", target = "cityName")
    @Mapping(source = "country", target = "countryName")
    Airport toAirportModel(AirportDto airportDto);
    
    @AfterMapping
    default void afterCreate(AirportDto airportDto, @MappingTarget Airport airport) {
        airport.setCity(new City(airportDto.getCityId()));
        airport.setCountry(new Country(airportDto.getCountryId()));
    }
}