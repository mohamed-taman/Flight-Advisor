package org.siriusxi.fa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.siriusxi.fa.api.model.response.CountryResponse;
import org.siriusxi.fa.infra.mapper.CountryMapper;
import org.siriusxi.fa.repository.CountryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Country controller used to handle countries API functions.
 *
 * @author Mohamed Taman
 * @version 1.0
 */

@Log4j2
@Tag(name = "Country Management",
        description = "A set of authorized APIs, for getting and managing system countries.")
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryRepository countryRepository;
    private final CountryMapper mapper;

    @Operation(summary = "Get all countries.",
            description = "Get Country or all countries.")
    @GetMapping
    public Set<CountryResponse> getAllCountries() {
        return this.mapper
                .toViews(this.countryRepository
                        .findAllByNameIgnoreCaseIsLike("%%"));
    }

    @Operation(summary = "Search all countries by name.",
            description = "Find country or all countries by name.")
    @GetMapping("search")
    public Set<CountryResponse> searchCountries(@RequestParam @NotBlank String name) {
        return this.mapper
                .toViews(this.countryRepository
                        .findAllByNameIgnoreCaseIsLike("%" + name + "%"));
    }

}
