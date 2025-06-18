package pl.edu.wit.hairsalon.notification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

import java.util.Set;

@Configuration
class NotificationConfiguration {

    @Bean
    NotificationFacade notificationFacade(NotificationPort notificationPort,
                                          Set<NotificationSender> notificationSenders) {
        var notificationSenderFactory = new NotificationSenderFactory(notificationSenders);
        return new LoggableNotificationFacade(
                new AppNotificationFacade(notificationPort, notificationSenderFactory)
        );
    }

    @Bean
    NotificationSender smsNotificationSender(SmsSenderPort smsSenderPort,
                                             NotificationCreator notificationCreator,
                                             NotificationPort notificationPort) {
        return new LoggableNotificationSender(
                new SmsNotificationSender(smsSenderPort, notificationCreator, notificationPort)
        );
    }

    @Bean
    NotificationSender emailNotificationSender(EmailSenderPort emailSenderPort,
                                               NotificationCreator notificationCreator,
                                               NotificationPort notificationPort) {
        return new LoggableNotificationSender(
                new EmailNotificationSender(emailSenderPort, notificationCreator, notificationPort)
        );
    }

    @Bean
    NotificationCreator notificationCreator(IdGenerator idGenerator) {
        return new NotificationCreator(idGenerator);
    }

}
