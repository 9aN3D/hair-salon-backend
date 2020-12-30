package pl.edu.wit.spring.adapter.repository.auth_details.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.auth_details.shared.Role;

import java.util.Set;

@Data
@Document
public class AuthDetailsDocument {

    @Id
    String id;

    @Indexed
    String email;

    String password;

    Set<Role> roles;

}
