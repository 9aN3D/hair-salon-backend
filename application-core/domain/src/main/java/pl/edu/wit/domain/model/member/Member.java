package pl.edu.wit.domain.model.member;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.domain.dto.MemberDto;
import pl.edu.wit.domain.exception.member.MemberNotValidException;
import pl.edu.wit.domain.model.PhoneNumber;
import pl.edu.wit.domain.model.StringNotBlank;
import pl.edu.wit.domain.model.auth_details.AuthDetails;

import java.time.LocalDateTime;
import java.util.Set;

import static java.util.Objects.nonNull;

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
        this.id = setId(id);
        this.name = setName(name);
        this.surname = setSurname(surname);
        this.phoneNumber = setPhoneNumber(phoneNumber);
        this.agreements = setAgreements(agreements);
        this.authDetails = setAuthDetails(authDetails);
        this.registrationDateTime = setRegistrationDateTime(registrationDateTime);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public Set<MemberAgreement> getAgreements() {
        return agreements.value();
    }

    public AuthDetails getAuthDetails() {
        return authDetails;
    }

    public LocalDateTime getRegistrationDateTime() {
        return registrationDateTime;
    }

    public MemberDto toDto() {
        return MemberDto.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .phoneNumber(phoneNumber.value())
                .agreements(getAgreements())
                .authDetails(authDetails.toDto())
                .registrationDateTime(registrationDateTime)
                .build();
    }

    private String setId(String id) {
        if (nonNull(id)) {
            return id;
        }
        throw new MemberNotValidException("Member id cannot be null");
    }

    private String setName(String name) {
        return new StringNotBlank(name).validate();
    }

    private String setSurname(String surname) {
        return new StringNotBlank(surname).validate();
    }

    private PhoneNumber setPhoneNumber(PhoneNumber phoneNumber) {
        if (nonNull(phoneNumber)) {
            return phoneNumber;
        }
        throw new MemberNotValidException("Member phone number cannot be null");
    }

    private MemberAgreements setAgreements(MemberAgreements agreements) {
        if (nonNull(agreements)) {
            return agreements;
        }
        throw new MemberNotValidException("Member agreements cannot be null");
    }

    private AuthDetails setAuthDetails(AuthDetails authDetails) {
        if (nonNull(authDetails)) {
            return authDetails;
        }
        throw new MemberNotValidException("Member auth details cannot be null");
    }

    private LocalDateTime setRegistrationDateTime(LocalDateTime registrationDateTime) {
        if (nonNull(registrationDateTime)) {
            return registrationDateTime;
        }
        throw new MemberNotValidException("Member registration date time cannot be null");
    }

}
