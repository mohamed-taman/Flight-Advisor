package org.siriusxi.htec.fa.repository;

import org.siriusxi.htec.fa.domain.model.Country;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@CacheConfig(cacheNames = "countries")
public interface CountryRepository extends CrudRepository<Country, Integer> {
    
    @Cacheable
    Optional<Country> findByNameIgnoreCaseIsLike(String name);
    
    Set<Country> findAllByNameIgnoreCaseIsLike(String name);
    
    /**
     * Return the country if exist, else save and return it.
     *
     * @param name of the country.
     * @return the found or saved country.
     */
    @Cacheable
    default Country findOrSaveBy(String name) {
        return findByNameIgnoreCaseIsLike(name.trim())
            .orElseGet(() -> save(new Country(name)));
    }
}
