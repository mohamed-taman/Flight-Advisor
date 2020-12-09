package org.siriusxi.htec.fa.domain.mapper;

import org.mapstruct.Mapper;
import org.siriusxi.htec.fa.domain.dto.response.CountryView;
import org.siriusxi.htec.fa.domain.model.Country;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    
    CountryView toView(Country country);
    
    Set<CountryView> toViews(Set<Country> countries);
}