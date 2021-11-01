package pl.edu.wit.hairsalon.member.command;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.authdetails.command.AuthDetailsUpdateCommand;
import pl.edu.wit.hairsalon.member.dto.MemberAgreementDto;

import java.util.Set;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MemberUpdateCommand extends MemberBaseCommand {

    @Builder
    public MemberUpdateCommand(String name, String surname, String email, String password, String phone, Set<MemberAgreementDto> agreements) {
        super(name, surname, email, password, phone, agreements);
    }

    public AuthDetailsUpdateCommand toAuthDetailsUpdateCommand() {
        return AuthDetailsUpdateCommand.builder()
                .email(getEmail())
                .password(getPassword())
                .build();
    }

    public Boolean isNotBlankEmail() {
        return !getEmail().isBlank();
    }

    public Boolean isNotBlankPassword() {
        return !getPassword().isBlank();
    }

}

