package pl.edu.wit.hairsalon.user;

import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.user.dto.UserContactDto;

record UserContact(String email) implements SelfValidator<UserContact> {

    @Override
    public UserContact validate() {
        validate(new NotBlankString(email));
        return this;
    }

    UserContactDto toDto() {
        return new UserContactDto(email);
    }

}
