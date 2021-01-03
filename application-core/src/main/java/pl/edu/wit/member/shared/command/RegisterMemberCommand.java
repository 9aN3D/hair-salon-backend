package pl.edu.wit.member.shared.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = "password")
@NoArgsConstructor
public class RegisterMemberCommand {

    private String name;

    private String surname;

    private String email;

    private String password;

    private String phone;

    private Boolean personalDataAgreement;

    private Boolean reservationReceiptAgreement;

    private Boolean marketingAgreement;

}
