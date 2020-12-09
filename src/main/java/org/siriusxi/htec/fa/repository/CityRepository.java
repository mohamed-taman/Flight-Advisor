package org.siriusxi.htec.fa.repository;

import org.siriusxi.htec.fa.domain.model.City;
import org.siriusxi.htec.fa.domain.model.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {
    
    Optional<City> findByCountryAndNameIgnoreCaseIsLike(Country country, String name);
    
    /**
     * Return the city if exist, else save and return it.
     *
     * @param name of the country.
     * @param country of the city.
     * @return the found or saved country.
     */
    default City findOrSaveBy(Country country, String name) {
        return findByCountryAndNameIgnoreCaseIsLike(country, name.trim())
            .orElseGet(() -> save(new City(name, country)));
    }
    
    
    /**
     * Search cities by name, it is case insensitive search.
     * @param name to search city by.
     * @return list of found cities, or empty list if not found.
     */
    List<City> findByNameIgnoreCaseIsLike(String name);
}
