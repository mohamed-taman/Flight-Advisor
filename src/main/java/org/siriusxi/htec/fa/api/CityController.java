package org.siriusxi.htec.fa.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.siriusxi.htec.fa.domain.dto.request.CreateCityRequest;
import org.siriusxi.htec.fa.domain.dto.request.SearchAirportRequest;
import org.siriusxi.htec.fa.domain.dto.request.UpSrtCommentRequest;
import org.siriusxi.htec.fa.domain.dto.request.SearchCityRequest;
import org.siriusxi.htec.fa.domain.dto.response.AirportView;
import org.siriusxi.htec.fa.domain.dto.response.CityView;
import org.siriusxi.htec.fa.domain.dto.response.CommentView;
import org.siriusxi.htec.fa.domain.dto.response.TripView;
import org.siriusxi.htec.fa.domain.model.Role;
import org.siriusxi.htec.fa.domain.model.User;
import org.siriusxi.htec.fa.service.CityMgmtService;
import org.siriusxi.htec.fa.service.TravelService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * City controller used to handle cities API functions.
 *
 * @author Mohamed Taman
 * @version 1.0
 *
 * FIXME: Swagger documentation
 */
@Log4j2
@Tag(name = "City Management")
@RestController
@RequestMapping("v1/cities")
public class CityController {
    
    private final CityMgmtService cityMgmtService;
    private final TravelService travelService;
    
    public CityController(CityMgmtService cityMgmtService, TravelService travelService) {
        this.cityMgmtService = cityMgmtService;
        this.travelService = travelService;
    }
    
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping
    public List<CityView> getAllCities(@RequestParam(defaultValue = "0")
                                           @Min(0) @Max(Integer.MAX_VALUE) int cLimit) {
        return cityMgmtService.searchCities(new SearchCityRequest(""), cLimit);
    }
    
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping("search")
    public List<CityView> searchCities(@RequestParam(defaultValue = "0")
                                           @Min(0) @Max(Integer.MAX_VALUE) int cLimit,
                                       @RequestBody @Valid SearchCityRequest request) {
        return cityMgmtService.searchCities(request, cLimit);
    }
    
    @RolesAllowed(Role.ADMIN)
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping
    public CityView createCity(@RequestBody @Valid CreateCityRequest request) {
        return cityMgmtService.addCity(request);
    }
    
    
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("travel")
    public List<TripView> travel(@RequestParam @Size(min = 3) String from,
                                 @RequestParam @Size(min = 3) String to) {
        
        if(from.isBlank() || to.isBlank())
            throw new IllegalArgumentException("""
                Neither source nor destination airport codes can be empty! \
                Please try with other valid values.
                """);
    
        if(from.trim().equals(to.trim()))
            throw new IllegalArgumentException(String.format(
                "You are traveling from and to the same destination [%s]",to));
        
        return travelService
                   .travel(from.trim().toUpperCase(), to.trim().toUpperCase());
    }
    
     /*
       Airport Management
    */
     @Operation(security = {@SecurityRequirement(name = "bearer-key")})
     @PostMapping("{id}/airports")
     public List<AirportView> searchAirports(@Parameter(description = "City Id")
                                               @PathVariable(name = "id")
                                               @Min(1) @Max(Integer.MAX_VALUE) int cityId,
                                             @RequestBody @Valid SearchAirportRequest request) {
         return cityMgmtService.searchAirports(request, cityId);
     }
     
    /*
       Comments Management
    */
    
    //Add comment
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping("{id}/comments")
    public CommentView addComment(@Parameter(description = "City Id")
                                         @PathVariable(name = "id")
                                         @Min(1) @Max(Integer.MAX_VALUE) int cityId,
                                     @RequestBody @Valid UpSrtCommentRequest request) {
        return cityMgmtService.addComment(getCurrentLoginUser(), cityId, request);
    }
    
    //update comment
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PutMapping("{id}/comments/{cid}")
    public void updateComment(@Parameter(description = "City Id")
                                         @PathVariable(name = "id")
                                         @Min(1) @Max(Integer.MAX_VALUE) int cityId,
                                     @Parameter(description = "Comment Id")
                                         @PathVariable("cid")
                                         @Min(1) @Max(Integer.MAX_VALUE) int commentId,
                                     @RequestBody @Valid UpSrtCommentRequest request) {
        
        cityMgmtService.updateComment(getCurrentLoginUser(),cityId, commentId,request);
    }
    
    //Delete comment
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping("{id}/comments/{cid}")
    public void deleteComment(@Parameter(description = "City Id") @PathVariable(name = "id")
                              @Min(1) @Max(Integer.MAX_VALUE) int cityId,
                              @Parameter(description = "Comment Id") @PathVariable("cid")
                              @Min(1) @Max(Integer.MAX_VALUE) int commentId) {
        
        cityMgmtService.deleteComment(getCurrentLoginUser(),cityId, commentId);
    }
    
    private User getCurrentLoginUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
