package org.siriusxi.htec.fa.repository;

import org.siriusxi.htec.fa.domain.model.Route;
import org.siriusxi.htec.fa.domain.model.RoutePK;
import org.siriusxi.htec.fa.domain.model.vo.RouteView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RouteRepository extends CrudRepository<Route, RoutePK> {

    @Query(value = """
        SELECT new org.siriusxi.htec.fa.domain.model.vo.RouteView (
               routePK.source, routePK.destination, price)
        FROM Route
        ORDER BY routePK.source ASC
        """)
    Set<RouteView> getAll();
    
    @Query(value = """
        SELECT sum(price)
        FROM Route
        WHERE routePK IN (:routePkS)
        """)
    double getTripCost(Iterable<RoutePK> routePkS);
}
