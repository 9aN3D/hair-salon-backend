package pl.edu.wit.hairsalon.setting;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;
import pl.edu.wit.hairsalon.sharedkernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedkernel.domain.NotBlankString;

import static java.util.Objects.requireNonNull;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
class Setting implements SelfValidator<Setting> {

    private final SettingId id;
    private final String value;

    @Override
    public Setting validate() {
        requireNonNull(id, "Setting id must not be null");
        validate(new NotBlankString(value));
        return this;
    }

    SettingDto toDto() {
        return SettingDto.builder()
                .id(SettingIdDto.valueOf(id.name()))
                .value(value)
                .build();
    }

}
