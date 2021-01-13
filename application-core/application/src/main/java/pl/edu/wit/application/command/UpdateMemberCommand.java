package pl.edu.wit.application.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(exclude = "password")
public class UpdateMemberCommand {

    private String name;

    private String surname;

    private String email;

    private String password;

    private String phone;

}
