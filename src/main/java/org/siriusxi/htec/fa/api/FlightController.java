package org.siriusxi.htec.fa.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Flight controller used to handle flights search and finding best flights costs.
 *
 * @author Mohamed Taman
 * @version 1.0
 */
@Log4j2
@Tag(name = "Flight Management")
@RestController
@RequestMapping("v1/flights")
public class FlightController {
}
