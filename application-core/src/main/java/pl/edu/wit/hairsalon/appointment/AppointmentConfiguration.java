package pl.edu.wit.hairsalon.appointment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.member.MemberFacade;
import pl.edu.wit.hairsalon.notification.NotificationFacade;
import pl.edu.wit.hairsalon.scheduledevent.ScheduledEventFacade;
import pl.edu.wit.hairsalon.setting.SettingFacade;
import pl.edu.wit.hairsalon.sharedkernel.port.secondary.IdGenerator;

@Configuration
class AppointmentConfiguration {

    @Bean
    AppointmentFacade appointmentFacade(AppointmentPort appointmentPort,
                                        IdGenerator idGenerator,
                                        ScheduledEventFacade scheduledEventFacade,
                                        MemberFacade memberFacade,
                                        NotificationFacade notificationFacade,
                                        SettingFacade settingFacade) {
        var creator = new AppointmentCreator(appointmentPort, idGenerator);
        var updater = new AppointmentUpdater();
        var resignation = new AppointmentResignation(appointmentPort, scheduledEventFacade);
        var notificationSender = new AppointmentNotificationSender(appointmentPort, memberFacade, notificationFacade, settingFacade);
        var appointmentReminders = new AppointmentReminders(appointmentPort, notificationSender);
        return new AppAppointmentFacade(appointmentPort, creator, updater, resignation, appointmentReminders);
    }

}
