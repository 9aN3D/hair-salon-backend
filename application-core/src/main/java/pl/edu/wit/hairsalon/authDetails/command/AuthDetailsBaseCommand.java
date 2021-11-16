package pl.edu.wit.hairsalon.authDetails.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsStatusDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public class AuthDetailsBaseCommand {

    private String email;

    private String password;

    private AuthDetailsStatusDto status;

}
