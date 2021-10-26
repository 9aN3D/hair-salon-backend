package pl.edu.wit;

import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import pl.edu.wit.common.configuration.init.AdminInitializer;
import pl.edu.wit.common.configuration.init.HairdresserInitializer;
import pl.edu.wit.common.configuration.init.ProductInitializer;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

@SpringBootApplication
@RequiredArgsConstructor
@SecurityScheme(name = "hair-salon-API", scheme = "Bearer", type = HTTP, bearerFormat = "JWT", in = HEADER)
public class Application extends SpringBootServletInitializer implements CommandLineRunner {

    private final AdminInitializer adminInitializer;
    private final ProductInitializer productInitializer;
    private final HairdresserInitializer hairdresserInitializer;

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        adminInitializer.createIfNecessary();
        productInitializer.createIfNecessary();
        hairdresserInitializer.createIfNecessary();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(Application.class);
    }

}
