package pl.edu.wit.hairsalon.notification;

import pl.edu.wit.hairsalon.notification.dto.SmsNotificationContentDto;
import pl.edu.wit.hairsalon.notification.dto.SmsShipmentDto;

public interface SmsSenderPort {

    SmsShipmentDto send(SmsNotificationContentDto content);

}
