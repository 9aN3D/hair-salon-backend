package pl.edu.wit.hairsalon.setting.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_ADDRESS_APARTMENT_NUMBER;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_ADDRESS_CITY;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_ADDRESS_COUNTRY;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_ADDRESS_POSTAL_CODE;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_ADDRESS_STREET_NAME;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_ADDRESS_STREET_NUMBER;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_CONTACT_EMAIL;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_CONTACT_PHONE_NUMBER;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_END_HOUR_WORK;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_NAME;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_START_HOUR_WORK;

@Getter
@RequiredArgsConstructor
public enum SettingGroupDto {

    SALON(Set.of(
            SALON_NAME,
            SALON_ADDRESS_STREET_NAME,
            SALON_ADDRESS_STREET_NUMBER,
            SALON_ADDRESS_APARTMENT_NUMBER,
            SALON_ADDRESS_POSTAL_CODE,
            SALON_ADDRESS_CITY,
            SALON_ADDRESS_COUNTRY,
            SALON_CONTACT_PHONE_NUMBER,
            SALON_CONTACT_EMAIL,
            SALON_START_HOUR_WORK,
            SALON_END_HOUR_WORK
    )),
    SALON_ADDRESS(Set.of(
            SALON_ADDRESS_STREET_NAME,
            SALON_ADDRESS_STREET_NUMBER,
            SALON_ADDRESS_APARTMENT_NUMBER,
            SALON_ADDRESS_POSTAL_CODE,
            SALON_ADDRESS_CITY,
            SALON_ADDRESS_COUNTRY
    ));

    private final Set<SettingIdDto> settingIds;
}
