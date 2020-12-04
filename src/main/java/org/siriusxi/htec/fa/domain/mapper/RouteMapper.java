package org.siriusxi.htec.fa.domain.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.siriusxi.htec.fa.domain.dto.upload.route.RouteDto;
import org.siriusxi.htec.fa.domain.model.Airport;
import org.siriusxi.htec.fa.domain.model.Route;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sourceAirport", ignore = true)
    @Mapping(target = "destinationAirport", ignore = true)
    @Mapping(source = "srcAirportCode", target = "sourceAirportName")
    @Mapping(source = "destAirportCode", target = "destinationAirportName")
    Route toRouteModel(RouteDto routeDto);
    
    @AfterMapping
    default void afterCreate(RouteDto routeDto, @MappingTarget Route route) {
        route.setSourceAirport(new Airport(routeDto.getSrcAirportId()));
        route.setDestinationAirport(new Airport(routeDto.getDestAirportId()));
    }
}