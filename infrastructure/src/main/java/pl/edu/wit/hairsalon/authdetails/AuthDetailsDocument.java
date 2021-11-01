package pl.edu.wit.hairsalon.authdetails;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsRoleDto;
import pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsStatusDto;

@Data
@Builder
@QueryEntity
@Document(value = "AuthDetails")
@EqualsAndHashCode(of = {"id", "email"})
class AuthDetailsDocument {

    @Id
    String id;

    @Indexed
    String email;

    String password;

    AuthDetailsStatusDto status;

    AuthDetailsRoleDto role;

}
