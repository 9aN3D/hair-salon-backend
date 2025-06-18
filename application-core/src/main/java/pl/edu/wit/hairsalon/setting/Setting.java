package pl.edu.wit.hairsalon.setting;

import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import static java.util.Objects.requireNonNull;

record Setting(SettingId id, String value) implements SelfValidator<Setting> {

    @Override
    public Setting validate() {
        requireNonNull(id, "Setting id must not be null");
        validate(new NotBlankString(value));
        return this;
    }

    SettingDto toDto() {
        return new SettingDto(
                SettingIdDto.valueOf(id.name()),
                value
        );
    }

}
