package pl.edu.wit.hairsalon.member.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MemberContactDto {

    String email;

    String phone;

}
