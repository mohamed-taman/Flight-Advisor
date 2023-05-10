package org.siriusxi.fa.repository;

import lombok.NonNull;
import org.siriusxi.fa.domain.Airport;
import org.siriusxi.fa.domain.City;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "airports")
public interface AirportRepository extends CrudRepository<Airport, Integer> {
    
    List<Airport> findAirportsByCityAndNameIgnoreCaseIsLike(City city, String name);
    
    @Cacheable
    @Query("SELECT a FROM Airport a WHERE a.iata = :code or a.icao = :code")
    Optional<Airport> findByCode(String code);
    
    @Cacheable
    @Override
    @NonNull
    Optional<Airport> findById(@NonNull Integer id);
    
    @Query("""
            SELECT a FROM Airport a
            WHERE LOWER(a.iata) LIKE %:name%
            OR LOWER(a.name) LIKE %:name%
            OR LOWER(a.city.name) LIKE %:name%
            OR LOWER(a.country.name) LIKE %:name%
        """)
    List<Airport> findAirportsByCityOrCountryName(String name);
}
