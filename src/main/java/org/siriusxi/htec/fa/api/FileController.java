package org.siriusxi.htec.fa.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Files Upload controller used to handle all data feeding the system like airports and routes.
 *
 * @author Mohamed Taman
 * @version 1.0
 */
@Log4j2
@Tag(name = "File Management")
@RestController
@RequestMapping("v1/upload")
public class FileController {
}
