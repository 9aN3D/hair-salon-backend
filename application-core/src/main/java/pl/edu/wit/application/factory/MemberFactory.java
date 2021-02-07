package pl.edu.wit.application.factory;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.application.command.AuthDetailsCreateCommand;
import pl.edu.wit.application.command.MemberRegisterCommand;
import pl.edu.wit.application.domain.model.NotBlankPhoneNumber;
import pl.edu.wit.application.domain.model.PhoneNumber;
import pl.edu.wit.application.domain.model.PossiblePhoneNumber;
import pl.edu.wit.application.domain.model.member.Member;
import pl.edu.wit.application.domain.model.member.MemberAgreements;
import pl.edu.wit.application.port.secondary.IdGenerator;
import pl.edu.wit.application.port.secondary.PhoneNumberProvider;

import static java.time.LocalDateTime.now;
import static pl.edu.wit.application.domain.model.auth_details.AuthDetailsRole.MEMBER;
import static pl.edu.wit.application.domain.model.auth_details.AuthDetailsStatus.ACTIVE;

@RequiredArgsConstructor
public class MemberFactory {

    private final IdGenerator idGenerator;
    private final AuthDetailsFactory authDetailsFactory;
    private final PhoneNumberProvider phoneNumberProvider;

    public Member createNewMember(MemberRegisterCommand command) {
        return Member.builder()
                .id(idGenerator.generate())
                .name(command.getName())
                .surname(command.getSurname())
                .authDetails(authDetailsFactory.createNewAuthDetails(buildCreateAuthDetailsCommand(command)))
                .agreements(new MemberAgreements(command.getAgreements()))
                .phoneNumber(preparePhoneNumber(command.getPhone()))
                .registrationDateTime(now())
                .build();
    }

    private AuthDetailsCreateCommand buildCreateAuthDetailsCommand(MemberRegisterCommand command) {
        return AuthDetailsCreateCommand.builder()
                .email(command.getEmail())
                .password(command.getPassword())
                .role(MEMBER)
                .status(ACTIVE)
                .build();
    }

    private PhoneNumber preparePhoneNumber(String phone) {
        return new PossiblePhoneNumber(
                new NotBlankPhoneNumber(phone),
                phoneNumberProvider
        );
    }

}
