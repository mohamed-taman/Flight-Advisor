package org.siriusxi.htec.fa.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.siriusxi.htec.fa.domain.dto.request.CreateCityRequest;
import org.siriusxi.htec.fa.domain.dto.response.CityView;
import org.siriusxi.htec.fa.domain.model.City;
import org.siriusxi.htec.fa.domain.model.Country;
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
    
    @Mapping(target = "comments", expression = """
        java( city.getComments() != null ? commentMapper.toViews(city.getComments()) : null )""")
    @Mapping(target = "country", source = "country.name")
    public abstract CityView toView(City city);

public abstract List<CityView> toViews(List<City> city);
}