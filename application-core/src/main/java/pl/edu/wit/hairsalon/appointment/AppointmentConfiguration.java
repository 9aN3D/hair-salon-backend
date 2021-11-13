package pl.edu.wit.hairsalon.appointment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.scheduledevent.ScheduledEventFacade;
import pl.edu.wit.hairsalon.sharedkernel.port.secondary.IdGenerator;

@Configuration
class AppointmentConfiguration {

    @Bean
    AppointmentFacade appointmentFacade(AppointmentPort appointmentPort,
                                        IdGenerator idGenerator,
                                        ScheduledEventFacade scheduledEventFacade) {
        var creator = new AppointmentCreator(appointmentPort, idGenerator);
        var updater = new AppointmentUpdater();
        var resignation = new AppointmentResignation(appointmentPort, scheduledEventFacade);
        return new AppAppointmentFacade(appointmentPort, creator, updater, resignation);
    }

}
