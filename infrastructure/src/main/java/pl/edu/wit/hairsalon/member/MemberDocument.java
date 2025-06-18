package pl.edu.wit.hairsalon.member;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.member.dto.MemberAgreementDto;
import pl.edu.wit.hairsalon.sharedKernel.document.EmbeddableFullName;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

@QueryEntity
@Document(value = "Member")
class MemberDocument {

    @Id
    private String id;

    private EmbeddableFullName fullName;

    private EmbeddableMemberContact contact;

    private Set<MemberAgreementDto> agreements;

    private LocalDateTime registrationDateTime;

    MemberDocument() {
    }

    MemberDocument(String id, EmbeddableFullName fullName, EmbeddableMemberContact contact,
                   Set<MemberAgreementDto> agreements, LocalDateTime registrationDateTime) {
        this.id = id;
        this.fullName = fullName;
        this.contact = contact;
        this.agreements = agreements;
        this.registrationDateTime = registrationDateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EmbeddableFullName getFullName() {
        return fullName;
    }

    public void setFullName(EmbeddableFullName fullName) {
        this.fullName = fullName;
    }

    public EmbeddableMemberContact getContact() {
        return contact;
    }

    public void setContact(EmbeddableMemberContact contact) {
        this.contact = contact;
    }

    public Set<MemberAgreementDto> getAgreements() {
        return agreements;
    }

    public void setAgreements(Set<MemberAgreementDto> agreements) {
        this.agreements = agreements;
    }

    public LocalDateTime getRegistrationDateTime() {
        return registrationDateTime;
    }

    public void setRegistrationDateTime(LocalDateTime registrationDateTime) {
        this.registrationDateTime = registrationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MemberDocument that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MemberDocument.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("fullName=" + fullName)
                .add("contact=" + contact)
                .add("agreements=" + agreements)
                .add("registrationDateTime=" + registrationDateTime)
                .toString();
    }

}
