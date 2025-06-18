package pl.edu.wit.hairsalon.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.notification.dto.SmsNotificationContentDto;
import pl.edu.wit.hairsalon.notification.dto.SmsShipmentDto;
import pl.edu.wit.hairsalon.notification.dto.SmsShipmentStatusDto;
import pl.smsapi.api.SmsFactory;
import pl.smsapi.exception.SmsapiException;

@Service
class SmsApiSenderAdapter implements SmsSenderPort {

    private final Logger log;
    private final SmsFactory smsApi;

    public SmsApiSenderAdapter(SmsFactory smsApi) {
        this.log = LoggerFactory.getLogger(SmsApiSenderAdapter.class);
        this.smsApi = smsApi;
    }

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
            return status.map(messageResponse -> new SmsShipmentDto(
                    messageResponse.getId(),
                    SmsShipmentStatusDto.valueOf(messageResponse.getStatus()))
            ).orElseGet(SmsShipmentDto::error);
        } catch (SmsapiException exception) {
            log.error("SmsApi exception message: {}", exception.getMessage(), exception);
            return SmsShipmentDto.error();
        }
    }

}
