package pl.edu.wit.hairsalon.member;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.member.dto.MemberAgreementDto;
import pl.edu.wit.hairsalon.member.dto.MemberContactDto;
import pl.edu.wit.hairsalon.member.dto.MemberFullNameDto;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@QueryEntity
@Document(value = "Member")
@EqualsAndHashCode(of = {"id"})
class MemberDocument {

    @Id
    private String id;

    private MemberFullNameDto fullName;

    private MemberContactDto contact;

    private Set<MemberAgreementDto> agreements;

    private LocalDateTime registrationDateTime;

}
