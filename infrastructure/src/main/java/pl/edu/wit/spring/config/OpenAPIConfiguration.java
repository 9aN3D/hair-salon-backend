package pl.edu.wit.spring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Hair salon API")
                        .description("Serwis który umożliwiaja rezerwację terminu usługi bezpośredniej u fryzjera")
                        .version("1.0"));
    }

}
