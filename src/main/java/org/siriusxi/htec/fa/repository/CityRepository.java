package org.siriusxi.htec.fa.repository;

import org.siriusxi.htec.fa.domain.model.City;
import org.siriusxi.htec.fa.domain.model.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {
    
    Optional<City> findByCountryAndNameIsLike(Country country, String name);
    
}
