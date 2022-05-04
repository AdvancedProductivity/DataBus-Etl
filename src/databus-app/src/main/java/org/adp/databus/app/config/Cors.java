package org.adp.databus.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zzq
 */
@Configuration
public class Cors implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfigurer.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.info("set cors config");
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
        ;
    }
}
