package com.boilerplate.bnppf.example.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /* Makes these converters available through the Spring object ConversionService (autowired)
    * use like in this example: Demand demand = conversionService.convert(demandDetails, Demand.class);
    * */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DemandDetailsToDemandConverter());
        registry.addConverter(new DemandToDemandDetailsConverter());
    }
}
