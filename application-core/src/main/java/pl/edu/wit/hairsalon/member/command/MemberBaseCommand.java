package pl.edu.wit.hairsalon.member.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.authdetails.command.AuthDetailsCreateCommand;
import pl.edu.wit.hairsalon.member.dto.MemberAgreementDto;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public abstract class MemberBaseCommand {

    private String name;

    private String surname;

    private String email;

    private String password;

    private String phone;

    private Set<MemberAgreementDto> agreements;

}
