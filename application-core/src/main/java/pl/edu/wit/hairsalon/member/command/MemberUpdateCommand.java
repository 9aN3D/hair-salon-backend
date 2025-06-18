package pl.edu.wit.hairsalon.member.command;

import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsUpdateCommand;
import pl.edu.wit.hairsalon.member.dto.MemberAgreementDto;

import java.util.Set;
import java.util.StringJoiner;

public record MemberUpdateCommand(
        String name,
        String surname,
        String email,
        String password,
        String phone,
        Set<MemberAgreementDto> agreements
) {

    @Override
    public String toString() {
        return new StringJoiner(", ", MemberUpdateCommand.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("surname='" + surname + "'")
                .add("email='" + email + "'")
                .add("phone='" + phone + "'")
                .add("agreements=" + agreements)
                .toString();
    }

    public AuthDetailsUpdateCommand toAuthDetailsUpdateCommand() {
        return AuthDetailsUpdateCommand.builder()
                .email(email)
                .password(password)
                .build();
    }

    public Boolean isNotBlankEmail() {
        return !email.isBlank();
    }

    public Boolean isNotBlankPassword() {
        return !password.isBlank();
    }

}

