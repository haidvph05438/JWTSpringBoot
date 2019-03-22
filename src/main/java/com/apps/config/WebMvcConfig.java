package com.apps.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author doanhai
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
