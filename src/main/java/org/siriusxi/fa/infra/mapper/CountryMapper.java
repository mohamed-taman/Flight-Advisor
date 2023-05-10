package org.siriusxi.fa.infra.mapper;

import org.mapstruct.Mapper;
import org.siriusxi.fa.api.model.response.CountryResponse;
import org.siriusxi.fa.domain.Country;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    
    CountryResponse toView(Country country);
    
    Set<CountryResponse> toViews(Set<Country> countries);
}