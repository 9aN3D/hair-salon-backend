package pl.edu.wit.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import pl.edu.wit.spring.adapter.persistence.auth_details.MongoAuthDetailsRepository;
import pl.edu.wit.spring.adapter.persistence.auth_details.model.AuthDetailsDocument;
import pl.edu.wit.spring.adapter.persistence.user.MongoUserRepository;
import pl.edu.wit.spring.adapter.persistence.user.model.UserDocument;

import static java.time.LocalDateTime.now;
import static pl.edu.wit.application.domain.model.auth_details.AuthDetailsRole.ADMIN;
import static pl.edu.wit.application.domain.model.auth_details.AuthDetailsStatus.ACTIVE;

@ComponentScan(value = {"pl.edu.wit"})
@SpringBootApplication
public class Application extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private MongoAuthDetailsRepository authDetailsRepository;
    @Autowired
    private MongoUserRepository userRepository;

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var admin = userRepository.findFirstByName("Admin");
        if (admin.isEmpty()) {
            var savedAuthDetails = authDetailsRepository.save(buildAuthDetailsDocument());
            userRepository.save(buildUserDocument(savedAuthDetails));
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(Application.class);
    }

    private AuthDetailsDocument buildAuthDetailsDocument() {
        return AuthDetailsDocument.builder()
                .email("admin@hairsalon.com")
                .password("$2y$12$V0jiZzPjEGv/ZgTCw5o4z.55YJxj0dBNduWIPGb/2ygDDGo2.T5HG")
                .status(ACTIVE)
                .role(ADMIN)
                .build();
    }

    private UserDocument buildUserDocument(AuthDetailsDocument authDetailsDocument) {
        return UserDocument.builder()
                .name("Admin")
                .surname("I")
                .authDetails(authDetailsDocument)
                .registrationDateTime(now())
                .build();
    }

}
