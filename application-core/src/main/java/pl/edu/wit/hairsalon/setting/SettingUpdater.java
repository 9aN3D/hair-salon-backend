package pl.edu.wit.hairsalon.setting;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;

@RequiredArgsConstructor
class SettingUpdater {

    private final SettingPort settingPort;

    SettingDto update(SettingIdDto settingId, String value) {
        var settingDto = settingPort.findOneOrThrow(settingId);
        var setting = buildSetting(settingDto, value).validate();
        return settingPort.save(setting.toDto());
    }

    private Setting buildSetting(SettingDto settingDto, String value) {
        return Setting.builder()
                .id(SettingId.valueOf(settingDto.id().name()))
                .value(value)
                .build();
    }

}
