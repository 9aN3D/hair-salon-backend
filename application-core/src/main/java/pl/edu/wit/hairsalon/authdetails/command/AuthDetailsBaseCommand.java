package pl.edu.wit.hairsalon.authdetails.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsStatusDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public class AuthDetailsBaseCommand {

    private String email;

    private String password;

    private AuthDetailsStatusDto status;

}
