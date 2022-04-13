package org.siriusxi.htec.fa.infra.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.siriusxi.htec.fa.api.model.request.CreateCityRequest;
import org.siriusxi.htec.fa.api.model.response.CityView;
import org.siriusxi.htec.fa.domain.City;
import org.siriusxi.htec.fa.domain.Country;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", imports = Collections.class)
public abstract class CityMapper {
    
    @Autowired
    protected CommentMapper commentMapper;
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "airports", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "country", source = "country")
    public abstract City toModel(CreateCityRequest request, Country country);
    
    @Mapping(target = "comments", expression = "java( city.getComments() != null ? commentMapper.toViews(city.getComments()) : null )")
    @Mapping(target = "country", source = "country.name")
    public abstract CityView toView(City city);
    
    public abstract List<CityView> toViews(List<City> city);
}