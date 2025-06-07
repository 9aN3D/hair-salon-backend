package pl.edu.wit.hairsalon.setting.dto;

import lombok.Builder;

@Builder
public record SettingDto(SettingIdDto id, String value) {

}
