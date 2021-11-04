package pl.edu.wit.hairsalon.setting;

import pl.edu.wit.hairsalon.setting.command.SettingCreateCommand;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;
import pl.edu.wit.hairsalon.setting.query.SettingGroupFindQuery;

import java.util.List;

public interface SettingFacade {

    SettingIdDto create(SettingCreateCommand command);

    void update(SettingIdDto settingId, String value);

    SettingDto findOne(SettingIdDto settingId);

    List<SettingDto> findAll(SettingGroupFindQuery findQuery);

}
