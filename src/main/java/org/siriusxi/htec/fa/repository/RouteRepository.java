package org.siriusxi.htec.fa.repository;

import org.siriusxi.htec.fa.domain.model.Route;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends CrudRepository<Route, Integer> {

}
