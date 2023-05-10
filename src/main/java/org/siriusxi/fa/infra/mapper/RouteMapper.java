package org.siriusxi.fa.infra.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.siriusxi.fa.domain.Route;
import org.siriusxi.fa.api.model.upload.route.RouteDto;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    
    @Mapping(source = "srcAirportCode", target = "routePK.source")
    @Mapping(source = "destAirportCode", target = "routePK.destination")
    @Mapping(source = "srcAirportId", target = "sourceAirport.airportId")
    @Mapping(source = "destAirportId", target = "destinationAirport.airportId")
    Route toModel(RouteDto routeDto);
}