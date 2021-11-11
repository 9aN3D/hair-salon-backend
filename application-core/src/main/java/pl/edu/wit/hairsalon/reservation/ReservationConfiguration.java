package pl.edu.wit.hairsalon.reservation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.appointment.AppointmentFacade;
import pl.edu.wit.hairsalon.hairdresser.HairdresserFacade;
import pl.edu.wit.hairsalon.member.MemberFacade;
import pl.edu.wit.hairsalon.service.ServiceFacade;

@Configuration
class ReservationConfiguration {

    @Bean
    ReservationFacade reservationFacade(ReservationPort reservationPort,
                                        MemberFacade memberFacade,
                                        HairdresserFacade hairdresserFacade,
                                        ServiceFacade serviceFacade,
                                        AppointmentFacade appointmentFacade) {
        var creator = new ReservationCreator();
        var calculator = new ReservationCalculator(memberFacade, hairdresserFacade, serviceFacade, appointmentFacade);
        return new AppReservationFacade(reservationPort, creator, calculator);
    }

}
