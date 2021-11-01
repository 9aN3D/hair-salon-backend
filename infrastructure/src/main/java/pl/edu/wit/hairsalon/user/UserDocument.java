package pl.edu.wit.hairsalon.user;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.user.dto.UserContactDto;
import pl.edu.wit.hairsalon.user.dto.UserFullNameDto;

import java.time.LocalDateTime;

@Data
@Builder
@QueryEntity
@Document(value = "User")
@EqualsAndHashCode(of = {"id"})
class UserDocument {

    @Id
    private String id;

    private UserFullNameDto fullName;

    private UserContactDto contact;

    private LocalDateTime registrationDateTime;

}
