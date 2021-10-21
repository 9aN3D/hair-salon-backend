package pl.edu.wit.application.command.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.application.domain.model.member.MemberAgreement;

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

    private Set<MemberAgreement> agreements;

}
