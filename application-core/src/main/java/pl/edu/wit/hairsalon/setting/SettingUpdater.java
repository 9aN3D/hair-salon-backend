package pl.edu.wit.hairsalon.setting;

import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;

class SettingUpdater {

    private final SettingPort settingPort;

    SettingUpdater(SettingPort settingPort) {
        this.settingPort = settingPort;
    }

    SettingDto update(SettingIdDto settingId, String value) {
        var settingDto = settingPort.findOneOrThrow(settingId);
        var setting = buildSetting(settingDto, value).validate();
        return settingPort.save(setting.toDto());
    }

    private Setting buildSetting(SettingDto settingDto, String value) {
        return new Setting(
                SettingId.valueOf(settingDto.id().name()),
                value
        );
    }

}
