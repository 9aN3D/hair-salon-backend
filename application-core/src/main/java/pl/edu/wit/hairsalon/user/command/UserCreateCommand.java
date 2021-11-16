package pl.edu.wit.hairsalon.user.command;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsCreateCommand;

import static pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsRoleDto.ADMIN;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserCreateCommand extends UserBaseCommand {

    @Builder
    UserCreateCommand(String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }

    public AuthDetailsCreateCommand toAuthDetailsCreateCommand() {
        return AuthDetailsCreateCommand.builder()
                .email(getEmail())
                .password(getPassword())
                .role(ADMIN)
                .build();
    }

}
