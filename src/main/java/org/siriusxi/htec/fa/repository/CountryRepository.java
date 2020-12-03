package org.siriusxi.htec.fa.repository;

import org.siriusxi.htec.fa.domain.model.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {

    Optional<Country> findByNameIsLike(String name);
}
