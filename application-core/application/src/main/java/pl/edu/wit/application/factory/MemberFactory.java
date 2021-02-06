package pl.edu.wit.application.factory;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.application.command.CreateAuthDetailsCommand;
import pl.edu.wit.application.command.RegisterMemberCommand;
import pl.edu.wit.application.command.UpdateMemberCommand;
import pl.edu.wit.application.port.secondary.IdGenerator;
import pl.edu.wit.application.port.secondary.PhoneNumberProvider;
import pl.edu.wit.domain.model.PhoneNumber;
import pl.edu.wit.domain.model.auth_details.AuthDetails;
import pl.edu.wit.domain.model.member.Member;
import pl.edu.wit.domain.model.member.MemberAgreements;

import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static pl.edu.wit.domain.model.auth_details.AuthDetailsRole.MEMBER;
import static pl.edu.wit.domain.model.auth_details.AuthDetailsStatus.ACTIVE;

@RequiredArgsConstructor
public class MemberFactory {

    private final IdGenerator idGenerator;
    private final AuthDetailsFactory authDetailsFactory;
    private final PhoneNumberProvider phoneNumberProvider;

    public Member createNewMember(RegisterMemberCommand command) {
        return Member.builder()
                .id(idGenerator.generate())
                .name(command.getName())
                .surname(command.getSurname())
                .authDetails(authDetailsFactory.createNewAuthDetails(buildCreateAuthDetailsCommand(command)))
                .agreements(buildMemberAgreements(command))
                .phoneNumber(verifyPhoneNumber(command.getPhone()))
                .registrationDateTime(now())
                .build();
    }

    public Member updateMember(UpdateMemberCommand command, Member oldMember) {
        return Member.builder()
                .id(oldMember.getId())
                .name(ofNullable(command.getName())
                        .orElseGet(oldMember::getName))
                .surname(ofNullable(command.getSurname())
                        .orElseGet(oldMember::getSurname))
                .authDetails(updateAuthDetails(command, oldMember))
                .agreements(new MemberAgreements(oldMember.getAgreements()))
                .phoneNumber(ofNullable(command.getPhone())
                        .map(this::verifyPhoneNumber)
                        .orElseGet(oldMember::getPhoneNumber))
                .registrationDateTime(oldMember.getRegistrationDateTime())
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

    private AuthDetails updateAuthDetails(UpdateMemberCommand command, Member oldMember) {
        var oldMemberAuthDetails = oldMember.getAuthDetails();
        if (isNull(command.getEmail()) && isNull(command.getPassword())) {
            return oldMemberAuthDetails;
        }
        return authDetailsFactory.updateAuthDetails(command, oldMemberAuthDetails);
    }

    private PhoneNumber verifyPhoneNumber(String phoneNumber) {
        return phoneNumberProvider.verify(phoneNumber);
    }

}
