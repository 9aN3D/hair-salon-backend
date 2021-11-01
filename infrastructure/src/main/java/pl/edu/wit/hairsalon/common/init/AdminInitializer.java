package pl.edu.wit.hairsalon.common.init;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.user.UserFacade;
import pl.edu.wit.hairsalon.user.command.UserCreateCommand;
import pl.edu.wit.hairsalon.user.query.UserFindQuery;

@Component
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserFacade userFacade;

    public void createIfNecessary() {
        var users = userFacade.findAll(UserFindQuery.builder().fullName("Admin").build(), PageRequest.of(0, 2));
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
