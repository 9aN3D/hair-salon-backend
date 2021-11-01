package pl.edu.wit.hairsalon.member;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.authdetails.AuthDetailsFacade;
import pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.member.command.MemberRegisterCommand;
import pl.edu.wit.hairsalon.member.dto.MemberDto;

import static java.time.LocalDateTime.now;
import static pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsRoleDto.MEMBER;

@RequiredArgsConstructor
class MemberRegistration {

    private final MemberPort memberPort;
    private final PhoneNumberPort phoneNumberPort;
    private final AuthDetailsFacade authDetailsFacade;

    MemberDto register(MemberRegisterCommand command) {
        var savedAuthDetails = saveAuthDetails(command);
        return trySaveNewMember(command, savedAuthDetails);
    }

    private AuthDetailsDto saveAuthDetails(MemberRegisterCommand command) {
        return authDetailsFacade.create(command.toAuthDetailsCreateCommand());
    }

    private MemberDto trySaveNewMember(MemberRegisterCommand command, AuthDetailsDto savedAuthDetails) {
        try {
            var member = createNewMember(command, savedAuthDetails)
                    .validate()
                    .verifyContactPhoneNumber(phoneNumberPort);
            var memberDto = member.toDto();
            memberPort.save(memberDto);
            return memberDto;
        } catch (Exception ex) {
            authDetailsFacade.remove(savedAuthDetails.getId());
            throw ex;
        }
    }

    private Member createNewMember(MemberRegisterCommand command, AuthDetailsDto savedAuthDetails) {
        return Member.builder()
                .id(savedAuthDetails.getId())
                .fullName(getFullName(command))
                .contact(getContact(command))
                .agreements(MemberAgreements.of(command.getAgreements()))
                .registrationDateTime(now())
                .build();
    }

    private MemberFullName getFullName(MemberRegisterCommand command) {
        return new MemberFullName(command.getName(), command.getSurname());
    }

    private MemberContact getContact(MemberRegisterCommand command) {
        return new MemberContact(command.getEmail(), new PhoneNumber(command.getPhone()));
    }

}
