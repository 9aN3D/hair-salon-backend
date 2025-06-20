package pl.edu.wit.hairsalon;

import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.edu.wit.hairsalon.common.init.AdminInitializer;
import pl.edu.wit.hairsalon.common.init.HairdresserInitializer;
import pl.edu.wit.hairsalon.common.init.ServiceInitializer;
import pl.edu.wit.hairsalon.common.init.SettingInitializer;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

@EnableScheduling
@SpringBootApplication
@SecurityScheme(name = "hair-salon-API", scheme = "Bearer", type = HTTP, bearerFormat = "JWT", in = HEADER)
class Application extends SpringBootServletInitializer implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final AdminInitializer adminInitializer;
    private final ServiceInitializer serviceInitializer;
    private final HairdresserInitializer hairdresserInitializer;
    private final SettingInitializer settingInitializer;

    public Application(AdminInitializer adminInitializer, ServiceInitializer serviceInitializer,
                       HairdresserInitializer hairdresserInitializer, SettingInitializer settingInitializer) {
        this.adminInitializer = adminInitializer;
        this.serviceInitializer = serviceInitializer;
        this.hairdresserInitializer = hairdresserInitializer;
        this.settingInitializer = settingInitializer;
    }

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("CommandLineRunner started");
        adminInitializer.createIfNecessary();
        serviceInitializer.createIfNecessary();
        hairdresserInitializer.createIfNecessary();
        hairdresserInitializer.createIfNecessary();
        settingInitializer.createIfNecessary();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(Application.class);
    }

}
