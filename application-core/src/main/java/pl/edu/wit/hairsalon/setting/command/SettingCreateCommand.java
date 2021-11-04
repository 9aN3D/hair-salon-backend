package pl.edu.wit.hairsalon.setting.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;

@Data
@NoArgsConstructor
public class SettingCreateCommand {

    private SettingIdDto id;

    private String value;

}
