package pl.edu.wit.hairsalon.user.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.authdetails.command.AuthDetailsUpdateCommand;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserUpdateCommand extends UserBaseCommand {

    UserUpdateCommand(String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }

    public AuthDetailsUpdateCommand toAuthDetailsUpdateCommand() {
        return AuthDetailsUpdateCommand.builder()
                .email(getEmail())
                .password(getPassword())
                .build();
    }

}
