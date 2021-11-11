package pl.edu.wit.hairsalon.appointment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.sharedkernel.port.secondary.IdGenerator;

@Configuration
class AppointmentConfiguration {

    @Bean
    AppointmentFacade appointmentFacade(AppointmentPort appointmentPort,
                                        IdGenerator idGenerator) {
        var creator = new AppointmentCreator(appointmentPort, idGenerator);
        var updater = new AppointmentUpdater();
        return new AppAppointmentFacade(appointmentPort, creator, updater);
    }

}
