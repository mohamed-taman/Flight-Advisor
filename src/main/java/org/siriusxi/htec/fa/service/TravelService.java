package org.siriusxi.htec.fa.service;

import es.usc.citius.hipster.algorithm.Algorithm;
import es.usc.citius.hipster.algorithm.Hipster;
import es.usc.citius.hipster.graph.GraphBuilder;
import es.usc.citius.hipster.graph.GraphSearchProblem;
import es.usc.citius.hipster.model.impl.WeightedNode;
import lombok.extern.log4j.Log4j2;
import org.siriusxi.htec.fa.domain.dto.response.AirportView;
import org.siriusxi.htec.fa.domain.dto.response.TripView;
import org.siriusxi.htec.fa.domain.mapper.AirportMapper;
import org.siriusxi.htec.fa.domain.model.Airport;
import org.siriusxi.htec.fa.domain.model.RoutePK;
import org.siriusxi.htec.fa.repository.AirportRepository;
import org.siriusxi.htec.fa.repository.RouteRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Log4j2
@Service
@CacheConfig(cacheNames = "travels")
public class TravelService {
    
    private final RouteRepository routeRepository;
    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;
    
    private record FinalTrip(String start,
                             List<String> through,
                             String end,
                             double totalCost) {
    }
    
    public TravelService(RouteRepository routeRepository,
                         AirportRepository airportRepository,
                         AirportMapper airportMapper) {
        this.routeRepository = routeRepository;
        this.airportRepository = airportRepository;
        this.airportMapper = airportMapper;
    }
    
    @Transactional(readOnly = true)
    @Cacheable
    public List<TripView> travel(String from, String to) {
        var trip = findShortestPath(from, to);
        return buildFinalTripViews(trip);
    }
    
    private List<TripView> buildFinalTripViews(FinalTrip trip) {
        var trips = new ArrayList<TripView>();
        
        if (trip.through.size() == 0) {
            routeRepository
                .findById(new RoutePK(trip.start(), trip.end()))
                .ifPresent(route ->
                               trips.add(newTripView(
                                   route.getSourceAirport(), route.getDestinationAirport(),
                                   null, route.getPrice())));
        } else {
            
            var airports = new LinkedList<Airport>();
            airportRepository.findByCode(trip.start()).ifPresent(airports::add);
            trip.through()
                .forEach(code ->
                             airportRepository
                                 .findByCode(code)
                                 .ifPresent(airports::add));
            airportRepository.findByCode(trip.end()).ifPresent(airports::add);
    
            var allDest = new ArrayList<>(trip.through());
            allDest.add(trip.end());
            
            String previous = trip.start();
            List<RoutePK> routePKs = new ArrayList<>();
            for (String airportCode : allDest) {
                routePKs.add(new RoutePK(previous, airportCode));
                previous = airportCode;
            }
            
            trips.add(newTripView(
                airports.getFirst(),
                airports.getLast(),
                airports.subList(1, airports.size() -1)
                    .stream()
                    .map(airport -> airportMapper.toTripView(airport,0))
                    .collect(Collectors.toList()),
                routeRepository.getTripCost(routePKs)));
        }
        
        return trips;
    }
    
    private TripView newTripView(Airport src, Airport dest,
                                 List<AirportView> through,
                                 double cost) {
        return new TripView(
            airportMapper.toTripView(src,0), through,
            airportMapper.toTripView(dest,0), cost);
    }
    
    private FinalTrip findShortestPath(String from, String to) {
        requireNonNull(from, "From cant be null.");
        requireNonNull(to, "To cant be null.");
        
        // Create graph builder with vertices and cost
        var builder = buildGraph(GraphBuilder.create());
    
        /*
           Create a simple weighted directed graph,
           where vertices are Strings and cost values are just doubles.
        */
        var graph = builder.createDirectedGraph();
        
        // Create the search problem. For graph problems, just use
        // the GraphSearchProblem util class to generate the problem with ease.
        var problem = GraphSearchProblem
                          .startingFrom(from).in(graph)
                          .takeCostsFromEdges()
                          .build();
        
        // Search the shortest path from source to destination
        final var result = Hipster.createDijkstra(problem).search(to);
        
        return getTrip(result);
    }
    
    private GraphBuilder<String, Double> buildGraph(GraphBuilder<String, Double> graph) {
        // Get all routes
        routeRepository
            .getAll()
            //Build vertices and their price as wight
            .forEach(route ->
                         graph
                             .connect(route.source())
                             .to(route.destination())
                             .withEdge(route.price()));
        return graph;
    }
    
    private static FinalTrip getTrip(Algorithm<Double, String, WeightedNode<Double, String, Double>>
                                         .SearchResult result) {
        
        var paths = result.getOptimalPaths().get(0);
        int lastIndex = result.getOptimalPaths().get(0).size() - 1;
        
        // calculate final cost
        
        return new FinalTrip(
            paths.get(0),
            lastIndex != 0? paths.subList(1, lastIndex): Collections.emptyList(),
            paths.get(lastIndex),
            result.getGoalNode().getCost());
    }
    
}
