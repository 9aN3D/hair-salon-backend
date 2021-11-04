package pl.edu.wit.hairsalon.setting.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(of = "id")
public class SettingDto {

    SettingIdDto id;

    String value;

}
