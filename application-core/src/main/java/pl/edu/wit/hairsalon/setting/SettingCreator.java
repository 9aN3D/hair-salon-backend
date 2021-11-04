package pl.edu.wit.hairsalon.setting;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.setting.command.SettingCreateCommand;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;

@RequiredArgsConstructor
class SettingCreator {

    private final SettingPort settingPort;

    SettingDto create(SettingCreateCommand command) {
        var newSetting = createNewSetting(command).validate();
        return settingPort.save(newSetting.toDto());
    }

    private Setting createNewSetting(SettingCreateCommand command) {
        return Setting.builder()
                .id(SettingId.valueOf(command.getId().name()))
                .value(command.getValue())
                .build();
    }

}
