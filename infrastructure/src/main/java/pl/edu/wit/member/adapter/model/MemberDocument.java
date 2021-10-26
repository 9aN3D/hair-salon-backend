package pl.edu.wit.member.adapter.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.member.domain.MemberAgreement;
import pl.edu.wit.auth.details.adapter.model.AuthDetailsDocument;

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
