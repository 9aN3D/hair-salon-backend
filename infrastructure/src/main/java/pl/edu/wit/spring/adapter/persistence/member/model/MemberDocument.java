package pl.edu.wit.spring.adapter.persistence.member.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.domain.model.member.MemberAgreement;
import pl.edu.wit.spring.adapter.persistence.auth_details.model.AuthDetailsDocument;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@QueryEntity
@Document(value = "Member")
@EqualsAndHashCode(of = {"id"})
public class MemberDocument {

    @Id
    private String id;

    private String name;

    private String surname;

    private String phoneNumber;

    private Set<MemberAgreement> agreements;

    @DBRef
    private AuthDetailsDocument authDetails;

    private LocalDateTime registrationDateTime;

}
