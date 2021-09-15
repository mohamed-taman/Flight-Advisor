package org.siriusxi.htec.fa.repository;

import org.siriusxi.htec.fa.domain.model.Route;
import org.siriusxi.htec.fa.domain.model.RoutePK;
import org.siriusxi.htec.fa.domain.model.vo.RouteView;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@CacheConfig(cacheNames = "travels")
public interface RouteRepository extends CrudRepository<Route, RoutePK> {
    
    @Query(value = """
        SELECT new org.siriusxi.htec.fa.domain.model.vo.RouteView
            (routePK.source, routePK.destination, price)
        FROM Route
        ORDER BY routePK.source ASC
        """)
    Set<RouteView> getAll();
    
    @Query(value = """
        SELECT sum(price)
        FROM Route
        WHERE routePK IN (:routePKs)
        """)
    double getTripCost(Iterable<RoutePK> routePKs);
    
    List<Route> findAllByRoutePKIn(Iterable<RoutePK> routePKs);
    
    @CacheEvict(key = "#p0.routePK.source + #p0.routePK.destination")
    @Override
    <S extends Route> S save(S s);
    
    @CacheEvict(allEntries = true)
    @Override
    <S extends Route> Iterable<S> saveAll(Iterable<S> iterable);
}
