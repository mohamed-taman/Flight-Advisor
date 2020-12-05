package org.siriusxi.htec.fa.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.siriusxi.htec.fa.domain.dto.upload.airport.AirportDto;
import org.siriusxi.htec.fa.domain.model.Airport;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    
    @Mapping(target = "destinationRoutes", ignore = true)
    @Mapping(target = "sourceRoutes", ignore = true)
    @Mapping(source = "city", target = "cityName")
    @Mapping(source = "country", target = "countryName")
    @Mapping(source = "cityId", target = "city.id")
    @Mapping(source = "countryId", target = "country.id")
    Airport toAirportModel(AirportDto airportDto);
}