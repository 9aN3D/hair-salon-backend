package pl.edu.wit.hairsalon.common.init;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.user.UserFacade;
import pl.edu.wit.hairsalon.user.command.UserCreateCommand;
import pl.edu.wit.hairsalon.user.query.UserFindQuery;

@Component
public class AdminInitializer {

    private final UserFacade userFacade;

    public AdminInitializer(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public void createIfNecessary() {
        var users = userFacade.findAll(UserFindQuery.fullName("Admin"), PageRequest.of(0, 2));
        if (users.isEmpty()) {
            userFacade.create(UserCreateCommand.builder()
                    .email("admin@hairsalon.com")
                    .password("Admin123$%")
                    .name("Admin")
                    .surname("I")
                    .build());
        }
    }

}
