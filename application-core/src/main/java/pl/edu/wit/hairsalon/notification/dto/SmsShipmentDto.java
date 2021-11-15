package pl.edu.wit.hairsalon.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import static pl.edu.wit.hairsalon.notification.dto.SmsShipmentStatusDto.ERROR;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SmsShipmentDto {

    String id;

    SmsShipmentStatusDto status;

    public static SmsShipmentDto error() {
        return SmsShipmentDto.builder()
                .status(ERROR)
                .build();
    }

}
