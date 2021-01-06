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

    public Member createNewMember(String authDetailsId, RegisterMemberCommand command) {
        return Member.builder()
                .id(idGenerator.generate())
                .name(command.getName())
                .surname(command.getSurname())
                .authDetailsId(authDetailsId)
                .agreements(MemberAgreements.builder()
                        .personalDataAgreement(command.getPersonalDataAgreement())
                        .reservationReceiptAgreement(command.getReservationReceiptAgreement())
                        .marketingAgreement(command.getMarketingAgreement())
                        .build())
                .phoneNumber(new PhoneNumber(command.getPhone()))
                .registrationDateTime(now())
                .build();
    }

    public CreateAuthDetailsCommand buildCreateAuthDetailsCommand(RegisterMemberCommand command) {
        return CreateAuthDetailsCommand.builder()
                .email(command.getEmail())
                .password(command.getPassword())
                .status(ACTIVE)
                .role(MEMBER)
                .build();
    }

}
