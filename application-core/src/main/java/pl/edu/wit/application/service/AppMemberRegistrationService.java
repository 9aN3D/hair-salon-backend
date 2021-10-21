package pl.edu.wit.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.application.command.member.MemberRegisterCommand;
import pl.edu.wit.application.domain.model.NotBlankPhoneNumber;
import pl.edu.wit.application.domain.model.PhoneNumber;
import pl.edu.wit.application.domain.model.PossiblePhoneNumber;
import pl.edu.wit.application.domain.model.auth_details.AuthDetails;
import pl.edu.wit.application.domain.model.member.Member;
import pl.edu.wit.application.domain.model.member.MemberAgreements;
import pl.edu.wit.application.dto.AuthDetailsDto;
import pl.edu.wit.application.port.primary.AuthDetailsService;
import pl.edu.wit.application.port.primary.MemberRegistrationService;
import pl.edu.wit.application.port.secondary.IdGenerator;
import pl.edu.wit.application.port.secondary.MemberDao;
import pl.edu.wit.application.port.secondary.PhoneNumberProvider;

import static java.time.LocalDateTime.now;

@Slf4j
@RequiredArgsConstructor
public class AppMemberRegistrationService implements MemberRegistrationService {

    private final MemberDao memberDao;
    private final AuthDetailsService authDetailsService;
    private final PhoneNumberProvider phoneNumberProvider;
    private final IdGenerator idGenerator;

    @Override
    public void register(MemberRegisterCommand command) {
        log.trace("Registering member {command: {}}", command);
        var savedAuthDetails = authDetailsService.save(command.getEmail(), command.getPassword());
        var member = createNewMember(command, savedAuthDetails);
        var savedMemberId = memberDao.save(member.toDto());
        log.info("Registered member {savedMemberId: {}}", savedMemberId);
    }

    public Member createNewMember(MemberRegisterCommand command, AuthDetailsDto savedAuthDetails) {
        return Member.builder()
                .id(idGenerator.generate())
                .name(command.getName())
                .surname(command.getSurname())
                .authDetails(new AuthDetails(savedAuthDetails))
                .agreements(new MemberAgreements(command.getAgreements()))
                .phoneNumber(preparePhoneNumber(command.getPhone()))
                .registrationDateTime(now())
                .build();
    }

    private PhoneNumber preparePhoneNumber(String phone) {
        return new PossiblePhoneNumber(
                new NotBlankPhoneNumber(phone),
                phoneNumberProvider
        );
    }

}
