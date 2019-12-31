package app.datacollect.twitchdata.api.config;

import app.datacollect.twitchdata.api.common.rest.sort.StringToSortByConverter;
import app.datacollect.twitchdata.api.common.rest.sort.StringToSortDirectionConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSortByConverter());
        registry.addConverter(new StringToSortDirectionConverter());
    }
}
