package pl.edu.wit.spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.wit.spring.adapter.persistence.auth_details.MongoAuthDetailsRepository;
import pl.edu.wit.spring.adapter.persistence.auth_details.model.AuthDetailsDocument;
import pl.edu.wit.spring.adapter.persistence.user.MongoUserRepository;
import pl.edu.wit.spring.adapter.persistence.user.model.UserDocument;

import static java.time.LocalDateTime.now;
import static pl.edu.wit.application.domain.model.auth_details.AuthDetailsRole.ADMIN;
import static pl.edu.wit.application.domain.model.auth_details.AuthDetailsStatus.ACTIVE;

@Component
@RequiredArgsConstructor
public class DefaultAdminConfiguration {

    private final MongoAuthDetailsRepository authDetailsRepository;
    private final MongoUserRepository userRepository;

    public void createIfNecessary() {
        var admin = userRepository.findFirstByName("Admin");
        if (admin.isEmpty()) {
            var savedAuthDetails = authDetailsRepository.save(buildAuthDetailsDocument());
            userRepository.save(buildUserDocument(savedAuthDetails));
        }
    }

    private AuthDetailsDocument buildAuthDetailsDocument() {
        return AuthDetailsDocument.builder()
                .email("admin@hairsalon.com")
                .password("$2y$12$EcjDiJwckWwH2mytPOa2weLxkFaZqMzhtoxoh6mOtmy8OsDSZmoD.")
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
