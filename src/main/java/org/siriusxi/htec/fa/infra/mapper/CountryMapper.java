package org.siriusxi.htec.fa.infra.mapper;

import org.mapstruct.Mapper;
import org.siriusxi.htec.fa.api.model.response.CountryView;
import org.siriusxi.htec.fa.domain.Country;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    
    CountryView toView(Country country);
    
    Set<CountryView> toViews(Set<Country> countries);
}