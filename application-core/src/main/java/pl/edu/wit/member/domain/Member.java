package pl.edu.wit.member.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.IdGenerator;
import pl.edu.wit.common.StringNotBlank;
import pl.edu.wit.member.shared.command.RegisterMemberCommand;

import java.time.LocalDateTime;
import java.util.Set;

@ToString
@EqualsAndHashCode(of = "id")
public class Member {

    private final String id;
    private final String name;
    private final String surname;
    private final PhoneNumber phoneNumber;
    private final MemberAgreements agreements;
    private final String authDetailsId;
    private final LocalDateTime registrationDateTime;

    public Member(IdGenerator idGenerator, String authDetailsId, LocalDateTime registrationDateTime, RegisterMemberCommand command) {
        this.id = idGenerator.generate();
        this.name = new StringNotBlank(command.getName()).validate();
        this.surname = new StringNotBlank(command.getSurname()).validate();
        this.phoneNumber = new PhoneNumber(command.getPhone());
        this.agreements = new MemberAgreements(command);
        this.registrationDateTime = registrationDateTime;
        this.authDetailsId = authDetailsId;
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

    public String getPhoneNumber() {
        return phoneNumber.value();
    }

    public Set<MemberAgreement> getAgreements() {
        return agreements.value();
    }

    public String getAuthDetailsId() {
        return authDetailsId;
    }

    public LocalDateTime getRegistrationDateTime() {
        return registrationDateTime;
    }

}
