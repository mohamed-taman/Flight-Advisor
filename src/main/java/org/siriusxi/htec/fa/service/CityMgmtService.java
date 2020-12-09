package org.siriusxi.htec.fa.service;

import lombok.extern.log4j.Log4j2;
import org.siriusxi.htec.fa.domain.dto.request.CreateCityRequest;
import org.siriusxi.htec.fa.domain.dto.request.SearchCityRequest;
import org.siriusxi.htec.fa.domain.dto.request.UpSrtCommentRequest;
import org.siriusxi.htec.fa.domain.dto.response.CityView;
import org.siriusxi.htec.fa.domain.dto.response.CommentView;
import org.siriusxi.htec.fa.domain.mapper.CityMapper;
import org.siriusxi.htec.fa.domain.mapper.CommentMapper;
import org.siriusxi.htec.fa.domain.model.City;
import org.siriusxi.htec.fa.domain.model.Comment;
import org.siriusxi.htec.fa.domain.model.Country;
import org.siriusxi.htec.fa.domain.model.User;
import org.siriusxi.htec.fa.infra.exception.NotAllowedException;
import org.siriusxi.htec.fa.infra.exception.NotFoundException;
import org.siriusxi.htec.fa.repository.CityRepository;
import org.siriusxi.htec.fa.repository.CommentRepository;
import org.siriusxi.htec.fa.repository.CountryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class CityMgmtService {
    
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final CityMapper cityMapper;
    
    private final String LIKE = "%";
    
    public CityMgmtService(CityRepository cityRepository, CityMapper cityMapper,
                           CountryRepository countryRepository,
                           CommentRepository commentRepository, CommentMapper commentMapper) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.commentRepository = commentRepository;
        this.cityMapper = cityMapper;
        this.commentMapper = commentMapper;
    }
    
    @Transactional
    public CityView addCity(CreateCityRequest cityRequest) {
        
        Country country;
        
        if (cityRequest.countryId() != 0) {
            country =
                countryRepository
                    .findById(cityRequest.countryId())
                    .orElseThrow(() -> new NotFoundException(Country.class,
                        cityRequest.countryId()));
        } else {
            country = countryRepository.findOrSaveBy(cityRequest.country());
        }
        
        City city = cityRepository.findOrSaveBy(country, cityRequest.name());
        
        /*
        When we create a new city it doesn't have any comments,
        so don't show empty comments.
         */
        city.setComments(null);
        
        return cityMapper.toView(city);
    }
    
    @Transactional(readOnly = true)
    public List<CityView> searchCities(SearchCityRequest request, int cLimit) {
        
        List<City> cities = new ArrayList<>();
        String searchWord = LIKE.concat(request.name()).concat(LIKE);
        
        if (cLimit != 0)
            for (City city : cityRepository.findByNameIgnoreCaseIsLike(searchWord)) {
                city.setComments(commentRepository
                                     .findByCity(city, PageRequest.of(0, cLimit)));
                cities.add(city);
            }
        
        else
            cities = cityRepository.findByNameIgnoreCaseIsLike(searchWord);
        
        return cityMapper.toViews(cities);
    }
    
    // City Comments management
    
    @Transactional
    public CommentView addComment(User user, int cityId,
                                  UpSrtCommentRequest request) {
        // Chick if the city is already exists
        City city = getCityIfExists(cityId);
        
        return commentMapper
                   .toView(commentRepository
                               .save(commentMapper
                                         .toNewModel(request, user, city)));
    }
    
    @Transactional
    public void updateComment(User user, int cityId, int commentId,
                              UpSrtCommentRequest request) {
        // Chick if the city is already exists
        City city = getCityIfExists(cityId);
        
        // If comment is exist then proceed
        commentRepository.findById(commentId)
            // Check if the comment is associated with given city
            .flatMap(comment ->
                         commentRepository.findByIdAndCity(comment.getId(), city))
            /* If exist check if user is allowed to update it */
            .map(found ->
                     commentRepository
                         .findByIdAndCityAndUser(found.getId(), city, user)
                         // If user is not allowed throw exception
                         .orElseThrow(() ->
                                          new NotAllowedException(Comment.class, found.getId(), "Update")))
            .ifPresent(comment -> commentMapper
                                      .toView(commentRepository
                                                  .save(commentMapper
                                                            .toUpdateModel(request, comment.getId(), user, city))));
    }
    
    public void deleteComment(User user, int cityId, int commentId) {
        // Chick if the city is already exists
        City city = getCityIfExists(cityId);
        
        // In all cases found or not it will return 200 because delete is Idempotent
        // If comment is exist then proceed
        commentRepository.findById(commentId)
            // Check if the comment is associated with given city
            .flatMap(comment -> commentRepository.findByIdAndCity(comment.getId(), city))
            // If exist check if user is allowed to delete it
            .ifPresent(found -> commentRepository.delete(commentRepository
                                                             .findByIdAndCityAndUser(found.getId(), city, user)
                                                             // If user is not allowed throw exception
                                                             .orElseThrow(() -> new NotAllowedException(Comment.class, found.getId(), "Delete"))));
    }
    
    private City getCityIfExists(int cityId) {
        return cityRepository
                   .findById(cityId)
                   .orElseThrow(() -> new NotFoundException(City.class, cityId));
    }
}
