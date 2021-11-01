package pl.edu.wit.hairsalon.member.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.authdetails.command.AuthDetailsCreateCommand;
import pl.edu.wit.hairsalon.member.dto.MemberAgreementDto;

import java.util.Set;

import static pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsRoleDto.MEMBER;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MemberRegisterCommand extends MemberBaseCommand {

    MemberRegisterCommand(String name, String surname, String email, String password, String phone, Set<MemberAgreementDto> agreements) {
        super(name, surname, email, password, phone, agreements);
    }

    public AuthDetailsCreateCommand toAuthDetailsCreateCommand() {
        return AuthDetailsCreateCommand.builder()
                .email(getEmail())
                .password(getPassword())
                .role(MEMBER)
                .build();
    }

}
