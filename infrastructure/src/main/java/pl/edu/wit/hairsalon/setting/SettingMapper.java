package pl.edu.wit.hairsalon.setting;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;

@Component
@Mapper(componentModel = "spring")
abstract class SettingMapper {

    abstract SettingDto toDto(SettingDocument settingDocument);

    abstract SettingDocument toDocument(SettingDto settingDto);

}
