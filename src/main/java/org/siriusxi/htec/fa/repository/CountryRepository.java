package org.siriusxi.htec.fa.repository;

import org.siriusxi.htec.fa.domain.model.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {

}
