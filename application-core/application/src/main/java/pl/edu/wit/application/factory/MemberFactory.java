package pl.edu.wit.application.factory;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.application.command.CreateAuthDetailsCommand;
import pl.edu.wit.application.command.RegisterMemberCommand;
import pl.edu.wit.application.port.secondary.IdGenerator;
import pl.edu.wit.domain.model.PhoneNumber;
import pl.edu.wit.domain.model.member.Member;
import pl.edu.wit.domain.model.member.MemberAgreements;

import static java.time.LocalDateTime.now;
import static pl.edu.wit.domain.model.auth_details.AuthDetailsRole.MEMBER;
import static pl.edu.wit.domain.model.auth_details.AuthDetailsStatus.ACTIVE;

@RequiredArgsConstructor
public class MemberFactory {

    private final IdGenerator idGenerator;
    private final AuthDetailsFactory authDetailsFactory;

    public Member createNewMember(RegisterMemberCommand command) {
        return Member.builder()
                .id(idGenerator.generate())
                .name(command.getName())
                .surname(command.getSurname())
                .authDetails(authDetailsFactory.createNewAuthDetails(buildCreateAuthDetailsCommand(command)))
                .agreements(buildMemberAgreements(command))
                .phoneNumber(new PhoneNumber(command.getPhone()))
                .registrationDateTime(now())
                .build();
    }

    private CreateAuthDetailsCommand buildCreateAuthDetailsCommand(RegisterMemberCommand command) {
        return CreateAuthDetailsCommand.builder()
                .email(command.getEmail())
                .password(command.getPassword())
                .role(MEMBER)
                .status(ACTIVE)
                .build();
    }

    private MemberAgreements buildMemberAgreements(RegisterMemberCommand command) {
        return MemberAgreements.builder()
                .personalDataAgreement(command.getPersonalDataAgreement())
                .reservationReceiptAgreement(command.getReservationReceiptAgreement())
                .marketingAgreement(command.getMarketingAgreement())
                .build();
    }

}
