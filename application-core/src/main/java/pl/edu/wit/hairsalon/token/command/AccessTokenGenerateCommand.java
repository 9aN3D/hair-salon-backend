package pl.edu.wit.hairsalon.token.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public class AccessTokenGenerateCommand {

    private String email;

    private String password;

}
