package pl.edu.wit.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import pl.edu.wit.spring.config.init.AdminInitializer;
import pl.edu.wit.spring.config.init.ProductInitializer;

@SpringBootApplication
@RequiredArgsConstructor
@ComponentScan(value = {"pl.edu.wit"})
public class Application extends SpringBootServletInitializer implements CommandLineRunner {

    private final AdminInitializer adminInitializer;
    private final ProductInitializer productInitializer;

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        adminInitializer.createIfNecessary();
        productInitializer.createIfNecessary();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(Application.class);
    }

}
