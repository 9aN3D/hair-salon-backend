package pl.edu.wit.hairsalon.setting;

import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.dto.SettingGroupDto;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;

import java.util.List;

public interface SettingPort {

    SettingDto save(SettingDto setting);

    SettingDto findOneOrThrow(SettingIdDto settingId);

    List<SettingDto> findAll(SettingGroupDto settingGroup);

}
