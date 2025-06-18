package pl.edu.wit.hairsalon.member;

import pl.edu.wit.hairsalon.authDetails.AuthDetailsFacade;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.member.command.MemberRegisterCommand;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.FullName;

import static java.time.LocalDateTime.now;

class MemberRegistration {

    private final MemberPort memberPort;
    private final PhoneNumberPort phoneNumberPort;
    private final AuthDetailsFacade authDetailsFacade;

    MemberRegistration(MemberPort memberPort, PhoneNumberPort phoneNumberPort, AuthDetailsFacade authDetailsFacade) {
        this.memberPort = memberPort;
        this.phoneNumberPort = phoneNumberPort;
        this.authDetailsFacade = authDetailsFacade;
    }

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
            authDetailsFacade.remove(savedAuthDetails.id());
            throw ex;
        }
    }

    private Member createNewMember(MemberRegisterCommand command, AuthDetailsDto savedAuthDetails) {
        return Member.builder()
                .id(savedAuthDetails.id())
                .fullName(getFullName(command))
                .contact(getContact(command))
                .agreements(MemberAgreements.of(command.agreements()))
                .registrationDateTime(now())
                .build();
    }

    private FullName getFullName(MemberRegisterCommand command) {
        return new FullName(command.name(), command.surname());
    }

    private MemberContact getContact(MemberRegisterCommand command) {
        return new MemberContact(command.email(), new PhoneNumber(command.phone()));
    }

}
