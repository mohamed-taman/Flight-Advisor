package org.siriusxi.htec.fa.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.siriusxi.htec.fa.domain.dto.upload.route.RouteDto;
import org.siriusxi.htec.fa.domain.model.Route;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    
    @Mapping(source = "srcAirportCode", target = "routePK.source")
    @Mapping(source = "destAirportCode", target = "routePK.destination")
    @Mapping(source = "srcAirportId", target = "sourceAirport.airportId")
    @Mapping(source = "destAirportId", target = "destinationAirport.airportId")
    Route toModel(RouteDto routeDto);
}