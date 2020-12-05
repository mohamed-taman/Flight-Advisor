package org.siriusxi.htec.fa.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.siriusxi.htec.fa.domain.model.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.Set;

/**
 * City controller used to handle cities API functions.
 *
 * @author Mohamed Taman
 * @version 1.0
 */
@Log4j2
@Tag(name = "Country Management")
@RolesAllowed(Role.ADMIN)
@RestController
@RequestMapping("v1/countries")
public class CountryController {
    
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping
    public Set<String> getAllCountries() {
        return Set.of("hello countries");
    }
    
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("search")
    public Set<String> searchCountries(@RequestParam String byName) {
        return Set.of("hello countries");
    }
    
}
