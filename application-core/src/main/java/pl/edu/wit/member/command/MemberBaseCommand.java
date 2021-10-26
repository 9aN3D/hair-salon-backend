package pl.edu.wit.member.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.member.domain.MemberAgreement;

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
