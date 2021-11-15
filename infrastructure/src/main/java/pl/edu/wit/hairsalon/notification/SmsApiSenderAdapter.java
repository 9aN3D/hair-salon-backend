package pl.edu.wit.hairsalon.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.notification.dto.SmsNotificationContentDto;
import pl.edu.wit.hairsalon.notification.dto.SmsShipmentDto;
import pl.edu.wit.hairsalon.notification.dto.SmsShipmentStatusDto;
import pl.smsapi.api.SmsFactory;
import pl.smsapi.exception.SmsapiException;

@Slf4j
@Service
@RequiredArgsConstructor
class SmsApiSenderAdapter implements SmsSenderPort {

    private final SmsFactory smsApi;

    @Override
    public SmsShipmentDto send(SmsNotificationContentDto content) {
        return trySend(content);
    }

    private SmsShipmentDto trySend(SmsNotificationContentDto content) {
        try {
            var action = smsApi.actionSend()
                    .setTo(content.getTo())
                    .setText(content.getBody());

            var result = action.execute();

            var status = result.getList().stream().findFirst();
            return status.isEmpty()
                    ? SmsShipmentDto.error()
                    : new SmsShipmentDto(status.get().getId(), SmsShipmentStatusDto.valueOf(status.get().getStatus()));
        } catch (SmsapiException exception) {
            log.error("SmsApi exception message: {}", exception.getMessage(), exception);
            return SmsShipmentDto.error();
        }
    }

}
