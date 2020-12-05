package org.siriusxi.htec.fa.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.siriusxi.htec.fa.domain.dto.request.CreateCityRequest;
import org.siriusxi.htec.fa.domain.dto.response.CityView;
import org.siriusxi.htec.fa.domain.model.City;
import org.siriusxi.htec.fa.domain.model.Country;

import java.util.Collections;

@Mapper(componentModel = "spring", imports = Collections.class)
public interface CityMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "airports", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(source = "request.description", target = "description")
    @Mapping(source = "request.name", target = "name")
    @Mapping(source = "country", target = "country")
    City toCityModel(CreateCityRequest request, Country country);
    
    @Mapping(target = "comments", expression = "java( Collections.emptyList() )")
    @Mapping(target = "country", source = "country.name")
    CityView toCityView(City city);
}