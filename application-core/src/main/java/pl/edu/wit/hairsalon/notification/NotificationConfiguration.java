package pl.edu.wit.hairsalon.notification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.sharedkernel.port.secondary.IdGenerator;

import java.util.Set;

@Configuration
class NotificationConfiguration {

    @Bean
    NotificationFacade notificationFacade(NotificationPort notificationPort,
                                          Set<NotificationSender> notificationSenders) {
        var notificationSenderFactory = new NotificationSenderFactory(notificationSenders);
        return new AppNotificationFacade(notificationPort, notificationSenderFactory);
    }

    @Bean
    NotificationSender smsNotificationSender(SmsSenderPort smsSenderPort,
                                             NotificationCreator notificationCreator,
                                             NotificationPort notificationPort) {
        return new SmsNotificationSender(smsSenderPort, notificationCreator, notificationPort);
    }

    @Bean
    NotificationSender emailNotificationSender(EmailSenderPort emailSenderPort,
                                               NotificationCreator notificationCreator,
                                               NotificationPort notificationPort) {
        return new EmailNotificationSender(emailSenderPort, notificationCreator, notificationPort);
    }

    @Bean
    NotificationCreator notificationCreator(IdGenerator idGenerator) {
        return new NotificationCreator(idGenerator);
    }

}
