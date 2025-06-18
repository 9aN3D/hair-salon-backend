package pl.edu.wit.hairsalon.setting;

import pl.edu.wit.hairsalon.setting.command.SettingCreateCommand;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;

class SettingCreator {

    private final SettingPort settingPort;

    SettingCreator(SettingPort settingPort) {
        this.settingPort = settingPort;
    }

    SettingDto create(SettingCreateCommand command) {
        var newSetting = createNewSetting(command).validate();
        return settingPort.save(newSetting.toDto());
    }

    private Setting createNewSetting(SettingCreateCommand command) {
        return new Setting(
                SettingId.valueOf(command.id().name()),
                command.value()
        );
    }

}
