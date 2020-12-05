package org.siriusxi.htec.fa.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.siriusxi.htec.fa.domain.dto.request.CreateCityRequest;
import org.siriusxi.htec.fa.domain.dto.request.CreateUpdateCommentRequest;
import org.siriusxi.htec.fa.domain.dto.request.SearchCityRequest;
import org.siriusxi.htec.fa.domain.dto.response.CityView;
import org.siriusxi.htec.fa.domain.dto.response.CommentView;
import org.siriusxi.htec.fa.domain.dto.response.TravelResultView;
import org.siriusxi.htec.fa.domain.model.Role;
import org.siriusxi.htec.fa.service.CityMgmtService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * City controller used to handle cities API functions.
 *
 * @author Mohamed Taman
 * @version 1.0
 */
@Log4j2
@Tag(name = "City Management")
@RestController
@RequestMapping("v1/cities")
public class CityController {
    
    private final CityMgmtService cityMgmtService;
    
    public CityController(CityMgmtService cityMgmtService) {
        this.cityMgmtService = cityMgmtService;
    }
    
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping
    public List<CityView> getAllCities(@RequestParam(defaultValue = "0") int commentsLimit) {
        return Collections.emptyList();
    }
    
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping("search")
    public List<CityView> searchCities(@RequestParam(defaultValue = "0") int commentsLimit,
                                       @RequestBody @Valid SearchCityRequest request) {
        return Collections.emptyList();
    }
    
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("travel")
    public List<TravelResultView> travel(@RequestParam String from,
                                         @RequestParam String to) {
        return Collections.emptyList();
    }
    
    @RolesAllowed(Role.ADMIN)
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping
    public CityView createCity(@RequestBody @Valid CreateCityRequest request) {
        return cityMgmtService.addCity(request);
    }
    
    /*
    Comments Management
    */
    
    //Add comment
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping("{id}/comments")
    public CommentView createComment(@Parameter(description = "City Id") @PathVariable int id,
                                     @RequestBody @Valid CreateUpdateCommentRequest request) {
        return new CommentView(1, request.description(), "Me",
            LocalDateTime.now(), null);
    }
    
    //Delete comment
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping("{id}/comments/{comment_id}")
    public void deleteComment(@Parameter(description = "City Id") @PathVariable int id,
                              @Parameter(description = "Comment Id")
                              @PathVariable("comment_id") int commentId) {
        log.info("Comment with Id {} is deleted for city {}", commentId, id);
    }
    
    //update comment
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PutMapping("{id}/comments/{comment_id}")
    public CommentView updateComment(@Parameter(description = "City Id") @PathVariable int id,
                                     @Parameter(description = "Comment Id") @PathVariable(
                                         "comment_id") int commentId,
                                     @RequestBody
                                     @Valid CreateUpdateCommentRequest request) {
        return new CommentView(1,
            request.description(),
            "Me Updated the comment",
            LocalDateTime.now().minusDays(1), LocalDateTime.now());
    }
}
