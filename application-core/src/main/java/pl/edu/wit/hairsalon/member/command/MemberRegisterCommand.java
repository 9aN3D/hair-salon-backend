package pl.edu.wit.hairsalon.member.command;

import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsCreateCommand;
import pl.edu.wit.hairsalon.member.dto.MemberAgreementDto;

import java.util.Set;
import java.util.StringJoiner;

import static pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsRoleDto.MEMBER;

public record MemberRegisterCommand(
        String name,
        String surname,
        String email,
        String password,
        String phone,
        Set<MemberAgreementDto> agreements
) {

    public AuthDetailsCreateCommand toAuthDetailsCreateCommand() {
        return AuthDetailsCreateCommand.builder()
                .email(email)
                .password(password)
                .role(MEMBER)
                .build();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MemberRegisterCommand.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("surname='" + surname + "'")
                .add("email='" + email + "'")
                .add("phone='" + phone + "'")
                .add("agreements=" + agreements)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String surname;
        private String email;
        private String password;
        private String phone;
        private Set<MemberAgreementDto> agreements;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder agreements(Set<MemberAgreementDto> agreements) {
            this.agreements = agreements;
            return this;
        }

        public MemberRegisterCommand build() {
            return new MemberRegisterCommand(
                    name,
                    surname,
                    email,
                    password,
                    phone,
                    agreements
            );
        }

    }

}
