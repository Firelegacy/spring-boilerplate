package com.boilerplate.bnppf.example.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DemandDetailsToDemandConverter());
        registry.addConverter(new DemandToDemandDetailsConverter());
    }
}
