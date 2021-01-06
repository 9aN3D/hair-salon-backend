package pl.edu.wit.spring.adapter.repository.member.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.domain.model.member.MemberAgreement;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Document(value = "Member")
public class MemberDocument {

    @Id
    private String id;

    private String name;

    private String surname;

    private String phoneNumber;

    private Set<MemberAgreement> agreements;

    private String authDetailsId;

    private LocalDateTime registrationDateTime;

}
