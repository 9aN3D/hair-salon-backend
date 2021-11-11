package pl.edu.wit.hairsalon.appointment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.hairdresser.HairdresserFacade;
import pl.edu.wit.hairsalon.member.MemberFacade;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.sharedkernel.port.secondary.IdGenerator;

@Configuration
class AppointmentConfiguration {

    @Bean
    AppointmentFacade appointmentFacade(AppointmentPort appointmentPort,
                                        IdGenerator idGenerator,
                                        ServiceFacade serviceFacade,
                                        MemberFacade memberFacade,
                                        HairdresserFacade hairdresserFacade) {
        var creator = new AppointmentCreator(appointmentPort, idGenerator, serviceFacade, memberFacade, hairdresserFacade);
        var updater = new AppointmentUpdater();
        return new AppAppointmentFacade(appointmentPort, creator, updater);
    }

}
