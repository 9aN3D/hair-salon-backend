package pl.edu.wit.hairsalon.setting.query;

import pl.edu.wit.hairsalon.setting.dto.SettingGroupDto;

public record SettingGroupFindQuery(SettingGroupDto group) {

    public static SettingGroupFindQuery ofSalonAddressGroup() {
        return new SettingGroupFindQuery(
                SettingGroupDto.SALON_ADDRESS
        );
    }

}
