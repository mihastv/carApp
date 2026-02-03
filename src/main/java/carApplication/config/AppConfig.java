package carApplication.config;

import carApplication.model.CustomCarBuilder;
import carApplication.model.CarConfigurationDirector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CustomCarBuilder carBuilder() {
        return new CustomCarBuilder();
    }

    @Bean
    public CarConfigurationDirector carDirector(CustomCarBuilder builder) {
        return new CarConfigurationDirector(builder);
    }
}