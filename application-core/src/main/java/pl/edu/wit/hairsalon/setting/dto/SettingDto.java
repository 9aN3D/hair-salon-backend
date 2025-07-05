package pl.edu.wit.hairsalon.setting.dto;

import java.time.Duration;

public record SettingDto(SettingIdDto id, String value) {

    public long valueToLong() {
        return Long.parseLong(value);
    }

    public Duration valueToDuration() {
        return Duration.ofMinutes(valueToLong());
    }

}
