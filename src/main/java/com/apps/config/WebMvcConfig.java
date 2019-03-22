package com.apps.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author doanhai
 *
 */
public class WebMvcConfig implements WebMvcConfigurer{

	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
