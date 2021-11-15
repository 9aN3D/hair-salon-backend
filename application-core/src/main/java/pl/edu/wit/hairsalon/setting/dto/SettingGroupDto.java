package pl.edu.wit.hairsalon.setting.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_LOGIN;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_OUTCOMING;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_PASSWORD;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_REQUIRE_SECURE_CONNECTION;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_SMTP_HOST;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_SMTP_PORT;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_SMS_API_TOKEN;
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
    )),
    NOTIFICATION_EMAIL(Set.of(
            NOTIFICATIONS_EMAIL_LOGIN,
            NOTIFICATIONS_EMAIL_PASSWORD,
            NOTIFICATIONS_EMAIL_SMTP_HOST,
            NOTIFICATIONS_EMAIL_SMTP_PORT,
            NOTIFICATIONS_EMAIL_REQUIRE_SECURE_CONNECTION,
            NOTIFICATIONS_EMAIL_OUTCOMING
    )),
    NOTIFICATION_SMS(Set.of(
            NOTIFICATIONS_SMS_API_TOKEN
    ));

    private final Set<SettingIdDto> settingIds;
}
