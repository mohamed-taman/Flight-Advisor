package org.siriusxi.htec.fa.service;

import lombok.extern.log4j.Log4j2;
import org.siriusxi.htec.fa.domain.dto.request.CreateCityRequest;
import org.siriusxi.htec.fa.domain.dto.response.CityView;
import org.siriusxi.htec.fa.domain.mapper.CityMapper;
import org.siriusxi.htec.fa.domain.model.Country;
import org.siriusxi.htec.fa.repository.CityRepository;
import org.siriusxi.htec.fa.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class CityMgmtService {
    
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final CityMapper cityMapper;
    
    
    public CityMgmtService(CityRepository cityRepository,
                           CountryRepository countryRepository,
                           CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.cityMapper = cityMapper;
    }
    
    @Transactional
    public CityView addCity(CreateCityRequest cityRequest) {
        
        Country country = new Country();
        
        if (cityRequest.countryId() != 0) {
            country.setId(cityRequest.countryId());
        } else {
        /*
        Check if the country is exists, if not save it,
        then in both cases assign the id to the new country.
         */
            String countryName = cityRequest.country().trim();
            
            country = countryRepository
                .findByNameIsLike(countryName)
                .orElseGet(() -> countryRepository
                    .save(new Country(countryName)));
        }
        
         /*
        Check if the city is exists, if not save it,
        then in both cases return the city view.
         */
    
        Country finalCountry = country;
        
        return cityMapper
            .toCityView(cityRepository
                .findByCountryAndNameIsLike(country, cityRequest.name())
                .orElseGet(() ->
                    cityRepository
                        .save(cityMapper
                            .toCityModel(cityRequest, finalCountry))));
    }
}
