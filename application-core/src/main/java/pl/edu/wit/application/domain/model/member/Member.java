package pl.edu.wit.application.domain.model.member;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.application.command.member.MemberUpdateCommand;
import pl.edu.wit.application.domain.model.NotBlankPhoneNumber;
import pl.edu.wit.application.domain.model.NotBlankString;
import pl.edu.wit.application.domain.model.PhoneNumber;
import pl.edu.wit.application.domain.model.PossiblePhoneNumber;
import pl.edu.wit.application.domain.model.auth_details.AuthDetails;
import pl.edu.wit.application.dto.MemberDto;
import pl.edu.wit.application.exception.ValidationException;
import pl.edu.wit.application.port.secondary.PasswordEncoder;
import pl.edu.wit.application.port.secondary.PhoneNumberProvider;

import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class Member {

    private final String id;
    private final String name;
    private final String surname;
    private final PhoneNumber phoneNumber;
    private final MemberAgreements agreements;
    private final AuthDetails authDetails;
    private final LocalDateTime registrationDateTime;

    public Member(String id,
                  String name,
                  String surname,
                  PhoneNumber phoneNumber,
                  MemberAgreements agreements,
                  AuthDetails authDetails,
                  LocalDateTime registrationDateTime) {
        this.id = new NotBlankString(id).value();
        this.name = new NotBlankString(name).value();
        this.surname = new NotBlankString(surname).value();
        this.phoneNumber = ofNullable(phoneNumber)
                .orElseThrow(() -> new ValidationException("Member phone number cannot be null"));
        this.agreements = ofNullable(agreements)
                .orElseThrow(() -> new ValidationException("Member agreements cannot be null"));
        this.authDetails = ofNullable(authDetails)
                .orElseThrow(() -> new ValidationException("Member auth details cannot be null"));
        this.registrationDateTime = ofNullable(registrationDateTime)
                .orElseThrow(() -> new ValidationException("Member registration date time cannot be null"));
    }

    public Member(MemberDto dto) {
        this(
                dto.getId(),
                dto.getName(),
                dto.getSurname(),
                new NotBlankPhoneNumber(dto.getPhoneNumber()),
                new MemberAgreements(dto.getAgreements()),
                new AuthDetails(dto.getAuthDetails()),
                dto.getRegistrationDateTime()
        );
    }

    public MemberDto toDto() {
        return MemberDto.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .phoneNumber(phoneNumber.value())
                .agreements(agreements.values())
                .authDetails(authDetails.toDto())
                .registrationDateTime(registrationDateTime)
                .build();
    }

    public Member update(MemberUpdateCommand command, PasswordEncoder passwordEncoder, PhoneNumberProvider phoneNumberProvider) {
        return Member.builder()
                .id(id)
                .name(ofNullable(command.getName()).orElse(name))
                .surname(ofNullable(command.getSurname()).orElse(surname))
                .agreements(ofNullable(command.getAgreements())
                        .map(MemberAgreements::new)
                        .orElse(agreements))
                .phoneNumber(ofNullable(command.getPhone())
                        .map(phoneNumber -> preparePhoneNumber(phoneNumber, phoneNumberProvider))
                        .orElse(phoneNumber))
                .authDetails(authDetails.update(command.toAuthDetailsUpdateCommand(), passwordEncoder))
                .registrationDateTime(registrationDateTime)
                .build();
    }

    private PhoneNumber preparePhoneNumber(String phone, PhoneNumberProvider phoneNumberProvider) {
        return new PossiblePhoneNumber(
                new NotBlankPhoneNumber(phone),
                phoneNumberProvider
        );
    }

    public String authDetailsId() {
        return authDetails.id();
    }

}
