package pl.edu.wit.hairsalon.setting.command;

import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;

public record SettingCreateCommand(SettingIdDto id, String value) {

}
