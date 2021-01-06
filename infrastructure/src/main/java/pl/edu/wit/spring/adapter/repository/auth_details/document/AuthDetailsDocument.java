package pl.edu.wit.spring.adapter.repository.auth_details.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.domain.model.auth_details.AuthDetailsRole;
import pl.edu.wit.domain.model.auth_details.AuthDetailsStatus;

@Data
@Document(value = "AuthDetails")
public class AuthDetailsDocument {

    @Id
    String id;

    @Indexed
    String email;

    String password;

    AuthDetailsStatus status;

    AuthDetailsRole role;

}
