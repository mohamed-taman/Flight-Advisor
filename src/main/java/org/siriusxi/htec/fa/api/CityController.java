package org.siriusxi.htec.fa.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.siriusxi.htec.fa.domain.model.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

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
    
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping
    public String getAllCities(){
        return "hello city";
    }
    
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @RolesAllowed(Role.ADMIN)
    @GetMapping("/create")
    public String createCity(){
        return "City is Added";
    }
}
