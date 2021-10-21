package pl.edu.wit.application.command.member;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.application.command.auth_details.AuthDetailsUpdateCommand;
import pl.edu.wit.application.domain.model.member.MemberAgreement;

import java.util.Set;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MemberUpdateCommand extends MemberBaseCommand {

    @Builder
    public MemberUpdateCommand(String name, String surname, String email, String password, String phone, Set<MemberAgreement> agreements) {
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

    public Boolean isNotBlankPhone() {
        return !getPhone().isBlank();
    }

}

