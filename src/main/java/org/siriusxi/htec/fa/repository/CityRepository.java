package org.siriusxi.htec.fa.repository;

import org.siriusxi.htec.fa.domain.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {

}
