package org.siriusxi.htec.fa.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * File upload configuration, is to manage the upload of large files.
 *
 * <p>As we cannot use Spring Boot’s default <code>StandardServletMultipartResolver</code> or
 * <code>CommonsMultipartResolver</code>, since the server has limited resources (disk space)
 * or memory for buffering.
 * So we need to disable the default <code>MultipartResolver</code> and define our own
 * <code>MultipartResolver</code>.</p>
 *
 * @author Mohamed Taman
 */

@Configuration
public class FileUploadConfig {
    
    @Bean("multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        var multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(-1);
        return multipartResolver;
    }
}
