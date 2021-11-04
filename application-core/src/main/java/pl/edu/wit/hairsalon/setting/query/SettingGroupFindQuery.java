package pl.edu.wit.hairsalon.setting.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.hairsalon.setting.dto.SettingGroupDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingGroupFindQuery {

    private SettingGroupDto group;

}
