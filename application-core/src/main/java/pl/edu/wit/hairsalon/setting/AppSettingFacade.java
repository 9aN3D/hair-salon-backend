package pl.edu.wit.hairsalon.setting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.hairsalon.setting.command.SettingCreateCommand;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;
import pl.edu.wit.hairsalon.setting.query.SettingGroupFindQuery;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Slf4j
@RequiredArgsConstructor
class AppSettingFacade implements SettingFacade {

    private final SettingPort settingPort;
    private final SettingCreator creator;
    private final SettingUpdater updater;

    @Override
    public SettingIdDto create(SettingCreateCommand command) {
        log.trace("Creating setting {command: {}}", command);
        requireNonNull(command, "Setting create command must not be null");
        var createdSetting = creator.create(command);
        log.info("Created setting {createdSetting: {}}", createdSetting);
        return createdSetting.id();
    }

    @Override
    public void update(SettingIdDto settingId, String value) {
        log.trace("Updating setting {id: {}, value: {}}", settingId, value);
        requireNonNull(settingId, "Setting id must not be null");
        requireNonNull(value, "Setting value must not be null");
        var updatedSetting = updater.update(settingId, value);
        log.info("Updated setting {updatedSetting: {}}", updatedSetting);
    }

    @Override
    public SettingDto findOne(SettingIdDto settingId) {
        log.trace("Getting setting {id: {}}", settingId);
        requireNonNull(settingId, "Setting id must not be null");
        var settingDto = settingPort.findOneOrThrow(settingId);
        log.info("Got setting {settingDto: {}}", settingDto);
        return settingDto;
    }

    @Override
    public List<SettingDto> findAll(SettingGroupFindQuery findQuery) {
        log.trace("Searching settings {findQuery: {}}", findQuery);
        var settings = settingPort.findAll(findQuery.getGroup());
        log.info("Searched settings {numberOfElements: {}}", settings.size());
        return settings;
    }

}
