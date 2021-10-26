package pl.edu.wit.auth.details.adapter.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.auth.details.domain.AuthDetailsRole;
import pl.edu.wit.auth.details.domain.AuthDetailsStatus;

@Data
@Builder
@QueryEntity
@Document(value = "AuthDetails")
@EqualsAndHashCode(of = {"id", "email"})
public class AuthDetailsDocument {

    @Id
    String id;

    @Indexed
    String email;

    String password;

    AuthDetailsStatus status;

    AuthDetailsRole role;

}
