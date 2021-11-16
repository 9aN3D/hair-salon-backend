package pl.edu.wit.hairsalon.authDetails.command;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsStatusDto;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AuthDetailsUpdateCommand extends AuthDetailsBaseCommand {

    @Builder
    public AuthDetailsUpdateCommand(String email, String password, AuthDetailsStatusDto status) {
        super(email, password, status);
    }

}
