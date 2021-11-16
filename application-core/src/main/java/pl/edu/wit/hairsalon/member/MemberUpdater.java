package pl.edu.wit.hairsalon.member;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.authDetails.AuthDetailsFacade;
import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsUpdateCommand;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.member.command.MemberUpdateCommand;
import pl.edu.wit.hairsalon.member.dto.MemberContactDto;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.member.dto.MemberFullNameDto;
import pl.edu.wit.hairsalon.member.query.MemberFindQuery;

import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

@RequiredArgsConstructor
class MemberUpdater {

    private final MemberPort memberPort;
    private final PhoneNumberPort phoneNumberPort;
    private final AuthDetailsFacade authDetailsFacade;

    MemberDto update(String memberId, MemberUpdateCommand command) {
        var memberDto = memberPort.findOneOrThrow(MemberFindQuery.withId(memberId));
        var previousAuthDetailsDto = authDetailsFacade.findOneById(memberId);
        var updatedAuthDetails = authDetailsFacade.update(memberId, command.toAuthDetailsUpdateCommand());
        return tryUpdateMember(previousAuthDetailsDto, command, memberDto, updatedAuthDetails);
    }

    private MemberDto tryUpdateMember(AuthDetailsDto previousAuthDetails,
                                      MemberUpdateCommand command,
                                      MemberDto member,
                                      AuthDetailsDto updatedAuthDetails) {
        try {
            var memberDto = buildMember(member, command, updatedAuthDetails)
                    .validate()
                    .toDto();
            memberPort.save(memberDto);
            return memberDto;
        } catch (Exception ex) {
            if (command.isNotBlankEmail() || command.isNotBlankPassword()) {
                authDetailsFacade.update(previousAuthDetails.getId(), AuthDetailsUpdateCommand.builder()
                        .email(command.getEmail())
                        .password(command.getPassword())
                        .build());
            }
            throw ex;
        }
    }

    public Member buildMember(MemberDto memberDto, MemberUpdateCommand command, AuthDetailsDto authDetailsDto) {
        return Member.builder()
                .id(memberDto.getId())
                .fullName(getFullName(memberDto.getFullName(), command))
                .contact(getContact(memberDto.getContact(), command, authDetailsDto))
                .agreements(getAgreements(memberDto, command))
                .registrationDateTime(memberDto.getRegistrationDateTime())
                .build();
    }

    private MemberFullName getFullName(MemberFullNameDto memberFullName, MemberUpdateCommand command) {
        return MemberFullName.builder()
                .name(ofNullable(command.getName())
                        .orElseGet(memberFullName::getName))
                .surname(ofNullable(command.getSurname())
                        .orElseGet(memberFullName::getSurname))
                .build();
    }

    private MemberContact getContact(MemberContactDto contact, MemberUpdateCommand command, AuthDetailsDto authDetailsDto) {
        return MemberContact.builder()
                .email(nonNull(command.getEmail())
                        ? authDetailsDto.getEmail()
                        : contact.getEmail())
                .phoneNumber(ofNullable(command.getPhone())
                        .map(PhoneNumber::new)
                        .map(phoneNumber -> phoneNumber.verifyPhoneNumber(phoneNumberPort))
                        .orElseGet(() -> new PhoneNumber(contact.getPhone())))
                .build();
    }

    private MemberAgreements getAgreements(MemberDto memberDto, MemberUpdateCommand command) {
        return ofNullable(command.getAgreements())
                .filter(not(Set::isEmpty))
                .map(MemberAgreements::of)
                .orElseGet(() -> MemberAgreements.of(memberDto.getAgreements()));
    }

}
