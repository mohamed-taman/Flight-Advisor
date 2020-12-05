package org.siriusxi.htec.fa.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.siriusxi.htec.fa.domain.dto.upload.route.RouteDto;
import org.siriusxi.htec.fa.domain.model.Route;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "srcAirportCode", target = "sourceAirportName")
    @Mapping(source = "destAirportCode", target = "destinationAirportName")
    @Mapping(source = "srcAirportId", target = "sourceAirport.airportId")
    @Mapping(source = "destAirportId", target = "destinationAirport.airportId")
    Route toRouteModel(RouteDto routeDto);
}