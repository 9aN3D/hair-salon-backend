package pl.edu.wit.hairsalon.user.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static java.util.Objects.nonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public abstract class UserBaseCommand {

    private String name;

    private String surname;

    private String email;

    private String password;

    public boolean isNotBlankEmail() {
        return nonNull(email) && !email.isBlank();
    }

    public boolean isNotBlankPassword() {
        return nonNull(password) && !password.isBlank();
    }

}
