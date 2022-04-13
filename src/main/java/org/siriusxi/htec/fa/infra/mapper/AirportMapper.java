package org.siriusxi.htec.fa.infra.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.siriusxi.htec.fa.api.model.response.AirportView;
import org.siriusxi.htec.fa.api.model.upload.airport.AirportDto;
import org.siriusxi.htec.fa.domain.Airport;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    
    @Mapping(target = "destinationRoutes", ignore = true)
    @Mapping(target = "sourceRoutes", ignore = true)
    @Mapping(target = "cityName", source = "city")
    @Mapping(target = "countryName", source = "country")
    @Mapping(target = "city.id", source = "cityId")
    @Mapping(target = "country.id", source = "countryId")
    Airport toModel(AirportDto airportDto);
    
    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "icao", expression = "java(null)")
    @Mapping(target = "city", source = "airport.cityName")
    @Mapping(target = "country", source = "airport.countryName")
    @Mapping(target = "airport", source = "airport.name")
    AirportView toTripView(Airport airport, int value);
    
    @Mapping(target = "id", source = "airportId")
    @Mapping(target = "city", source = "cityName")
    @Mapping(target = "country", source = "countryName")
    @Mapping(target = "airport", source = "name")
    AirportView toView(Airport airport);
    
    List<AirportView> toView(List<Airport> airport);
}