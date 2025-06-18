package pl.edu.wit.hairsalon.member;

import pl.edu.wit.hairsalon.authDetails.AuthDetailsFacade;
import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsUpdateCommand;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.member.command.MemberUpdateCommand;
import pl.edu.wit.hairsalon.member.dto.MemberContactDto;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.member.query.MemberFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.domain.FullName;
import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;

import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

class MemberUpdater {

    private final MemberPort memberPort;
    private final PhoneNumberPort phoneNumberPort;
    private final AuthDetailsFacade authDetailsFacade;

    public MemberUpdater(MemberPort memberPort, PhoneNumberPort phoneNumberPort, AuthDetailsFacade authDetailsFacade) {
        this.memberPort = memberPort;
        this.phoneNumberPort = phoneNumberPort;
        this.authDetailsFacade = authDetailsFacade;
    }

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
                authDetailsFacade.update(previousAuthDetails.id(), AuthDetailsUpdateCommand.builder()
                        .email(command.email())
                        .password(command.password())
                        .build());
            }
            throw ex;
        }
    }

    public Member buildMember(MemberDto memberDto, MemberUpdateCommand command, AuthDetailsDto authDetailsDto) {
        return Member.builder()
                .id(memberDto.id())
                .fullName(getFullName(memberDto.fullName(), command))
                .contact(getContact(memberDto.contact(), command, authDetailsDto))
                .agreements(getAgreements(memberDto, command))
                .registrationDateTime(memberDto.registrationDateTime())
                .build();
    }

    private FullName getFullName(FullNameDto memberFullName, MemberUpdateCommand command) {
        return new FullName(
                ofNullable(command.name()).orElseGet(memberFullName::name),
                ofNullable(command.surname()).orElseGet(memberFullName::surname)
        );
    }

    private MemberContact getContact(MemberContactDto contact, MemberUpdateCommand command, AuthDetailsDto authDetailsDto) {
        return new MemberContact(
                nonNull(command.email()) ? authDetailsDto.email() : contact.email(),
                getPhoneNumber(contact, command)
        );
    }

    private PhoneNumber getPhoneNumber(MemberContactDto contact, MemberUpdateCommand command) {
        return ofNullable(command.phone())
                .map(PhoneNumber::new)
                .map(phoneNumber -> phoneNumber.verifyPhoneNumber(phoneNumberPort))
                .orElseGet(() -> new PhoneNumber(contact.phone()));
    }

    private MemberAgreements getAgreements(MemberDto memberDto, MemberUpdateCommand command) {
        return ofNullable(command.agreements())
                .filter(not(Set::isEmpty))
                .map(MemberAgreements::of)
                .orElseGet(() -> MemberAgreements.of(memberDto.agreements()));
    }

}
