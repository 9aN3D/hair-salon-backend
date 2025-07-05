package pl.edu.wit.hairsalon.common.init;

import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.setting.SettingFacade;
import pl.edu.wit.hairsalon.setting.command.SettingCreateCommand;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;
import pl.edu.wit.hairsalon.setting.query.SettingGroupFindQuery;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_LOGIN;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_OUTCOMING;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_PASSWORD;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_REQUIRE_SECURE_CONNECTION;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_SMTP_HOST;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_SMTP_PORT;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_SMS_API_TOKEN;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.RESERVATION_GAP_MINUTES;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.RESERVATION_INTERVAL_MINUTES;
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

@Component
public class SettingInitializer {

    private final SettingFacade settingFacade;

    public SettingInitializer(SettingFacade settingFacade) {
        this.settingFacade = settingFacade;
    }

    public void createIfNecessary() {
        var existingSettings = settingFacade.findAll(new SettingGroupFindQuery(null)).stream()
                .collect(Collectors.toMap(SettingDto::id, Function.identity()));

        var defaultSettings = getDefaultSettings();

        defaultSettings.forEach((key, value) -> {
            if (!existingSettings.containsKey(key)) {
                settingFacade.create(new SettingCreateCommand(key, value));
            }
        });
    }

    private Map<SettingIdDto, String> getDefaultSettings() {
        var result = new HashMap<SettingIdDto, String>();
        result.put(SALON_NAME, "Salon Urody Bella");
        result.put(SALON_ADDRESS_STREET_NAME, "Kwiatowa");
        result.put(SALON_ADDRESS_STREET_NUMBER, "15");
        result.put(SALON_ADDRESS_APARTMENT_NUMBER, "3B");
        result.put(SALON_ADDRESS_POSTAL_CODE, "00-123");
        result.put(SALON_ADDRESS_CITY, "Warszawa");
        result.put(SALON_ADDRESS_COUNTRY, "Polska");
        result.put(SALON_CONTACT_PHONE_NUMBER, "+48 600 123 456");
        result.put(SALON_CONTACT_EMAIL, "kontakt@bellasalon.pl");
        result.put(SALON_START_HOUR_WORK, "8");
        result.put(SALON_END_HOUR_WORK, "20");
        result.put(NOTIFICATIONS_EMAIL_LOGIN, "powiadomienia@bellasalon.pl");
        result.put(NOTIFICATIONS_EMAIL_PASSWORD, "SuperTajneHaslo123!");
        result.put(NOTIFICATIONS_EMAIL_SMTP_HOST, "smtp.bellasalon.pl");
        result.put(NOTIFICATIONS_EMAIL_SMTP_PORT, "587");
        result.put(NOTIFICATIONS_EMAIL_REQUIRE_SECURE_CONNECTION, "true");
        result.put(NOTIFICATIONS_EMAIL_OUTCOMING, "powiadomienia@bellasalon.pl");
        result.put(NOTIFICATIONS_SMS_API_TOKEN, "abc123def456ghi789");
        result.put(RESERVATION_GAP_MINUTES, "10");
        result.put(RESERVATION_INTERVAL_MINUTES, "15");
        return result;
    }

}
