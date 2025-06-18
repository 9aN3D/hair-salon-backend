package pl.edu.wit.hairsalon.user;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.sharedKernel.document.EmbeddableFullName;
import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;
import pl.edu.wit.hairsalon.user.dto.UserContactDto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

@QueryEntity
@Document(value = "User")
class UserDocument {

    @Id
    private String id;

    private EmbeddableFullName fullName;

    private EmbeddableUserContact contact;

    private LocalDateTime registrationDateTime;

    UserDocument() {
    }

    UserDocument(String id, EmbeddableFullName fullName,
                 EmbeddableUserContact contact, LocalDateTime registrationDateTime) {
        this.id = id;
        this.fullName = fullName;
        this.contact = contact;
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

    public EmbeddableUserContact getContact() {
        return contact;
    }

    public void setContact(EmbeddableUserContact contact) {
        this.contact = contact;
    }

    public LocalDateTime getRegistrationDateTime() {
        return registrationDateTime;
    }

    public void setRegistrationDateTime(LocalDateTime registrationDateTime) {
        this.registrationDateTime = registrationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserDocument that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserDocument.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("fullName=" + fullName)
                .add("contact=" + contact)
                .add("registrationDateTime=" + registrationDateTime)
                .toString();
    }

}
