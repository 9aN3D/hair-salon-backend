package pl.edu.wit.hairsalon.notification.dto;

import static pl.edu.wit.hairsalon.notification.dto.SmsShipmentStatusDto.ERROR;

public record SmsShipmentDto(String id, SmsShipmentStatusDto status) {

    public static SmsShipmentDto error() {
        return new SmsShipmentDto(null, ERROR);
    }

}
