package pl.edu.wit.spring.adapter.persistence.user.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.spring.adapter.persistence.auth_details.model.AuthDetailsDocument;

import java.time.LocalDateTime;

@Data
@Builder
@QueryEntity
@Document(value = "User")
@EqualsAndHashCode(of = {"id"})
public class UserDocument {

    @Id
    private String id;

    private String name;

    private String surname;

    @DBRef
    private AuthDetailsDocument authDetails;

    private LocalDateTime registrationDateTime;

}
