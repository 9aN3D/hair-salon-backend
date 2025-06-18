package pl.edu.wit.hairsalon.setting;

import pl.edu.wit.hairsalon.setting.command.SettingCreateCommand;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;
import pl.edu.wit.hairsalon.setting.query.SettingGroupFindQuery;

import java.util.List;

import static java.util.Objects.requireNonNull;

class AppSettingFacade implements SettingFacade {

    private final SettingPort settingPort;
    private final SettingCreator creator;
    private final SettingUpdater updater;

    AppSettingFacade(SettingPort settingPort, SettingCreator creator, SettingUpdater updater) {
        this.settingPort = settingPort;
        this.creator = creator;
        this.updater = updater;
    }

    @Override
    public SettingIdDto create(SettingCreateCommand command) {
        requireNonNull(command, "Setting create command must not be null");
        return creator.create(command).id();
    }

    @Override
    public void update(SettingIdDto settingId, String value) {
        requireNonNull(settingId, "Setting id must not be null");
        requireNonNull(value, "Setting value must not be null");
        updater.update(settingId, value);
    }

    @Override
    public SettingDto findOne(SettingIdDto settingId) {
        requireNonNull(settingId, "Setting id must not be null");
        return settingPort.findOneOrThrow(settingId);
    }

    @Override
    public List<SettingDto> findAll(SettingGroupFindQuery findQuery) {
        requireNonNull(findQuery, "Setting group find query must not be null");
        return settingPort.findAll(findQuery.group());
    }
}
