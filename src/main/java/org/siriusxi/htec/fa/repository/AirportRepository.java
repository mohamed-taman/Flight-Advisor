package org.siriusxi.htec.fa.repository;

import org.siriusxi.htec.fa.domain.model.Airport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends CrudRepository<Airport, Integer> {

}
