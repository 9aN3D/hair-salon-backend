package pl.edu.wit.hairsalon.setting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wit.hairsalon.setting.command.SettingCreateCommand;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;
import pl.edu.wit.hairsalon.setting.query.SettingGroupFindQuery;

import java.util.List;

class LoggableSettingFacade implements SettingFacade {

    private final Logger log;
    private final SettingFacade delegate;

    LoggableSettingFacade(SettingFacade delegate) {
        this.log = LoggerFactory.getLogger(LoggableSettingFacade.class);
        this.delegate = delegate;
    }

    @Override
    public SettingIdDto create(SettingCreateCommand command) {
        log.trace("Creating setting {command: {}}", command);
        var createdId = delegate.create(command);
        log.info("Created setting {id: {}}", createdId);
        return createdId;
    }

    @Override
    public void update(SettingIdDto settingId, String value) {
        log.trace("Updating setting {id: {}, value: {}}", settingId, value);
        delegate.update(settingId, value);
        log.info("Updated setting {id: {}, value: {}}", settingId, value);
    }

    @Override
    public SettingDto findOne(SettingIdDto settingId) {
        log.trace("Getting setting {id: {}}", settingId);
        var settingDto = delegate.findOne(settingId);
        log.info("Got setting {dto: {}}", settingDto);
        return settingDto;
    }

    @Override
    public List<SettingDto> findAll(SettingGroupFindQuery findQuery) {
        log.trace("Searching settings {findQuery: {}}", findQuery);
        var result = delegate.findAll(findQuery);
        log.info("Searched settings {numberOfElements: {}}", result.size());
        return result;
    }

}
