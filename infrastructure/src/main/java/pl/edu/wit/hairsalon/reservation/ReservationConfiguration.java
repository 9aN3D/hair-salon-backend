package pl.edu.wit.hairsalon.reservation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.eventBus.DomainEventPublisher;
import pl.edu.wit.hairsalon.hairdresser.HairdresserFacade;
import pl.edu.wit.hairsalon.member.MemberFacade;
import pl.edu.wit.hairsalon.scheduledEvent.ScheduledEventFacade;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

@Configuration
class ReservationConfiguration {

    @Bean
    ReservationFacade reservationFacade(ReservationPort reservationPort,
                                        MemberFacade memberFacade,
                                        HairdresserFacade hairdresserFacade,
                                        ServiceFacade serviceFacade,
                                        ScheduledEventFacade scheduledEventFacade,
                                        IdGenerator idGenerator,
                                        DomainEventPublisher eventPublisher) {
        var calculator = new ReservationCalculator(memberFacade, hairdresserFacade, serviceFacade, scheduledEventFacade);
        var creator = new ReservationCreator(idGenerator, reservationPort, calculator, eventPublisher);
        return new AppReservationFacade(reservationPort, creator, calculator);
    }

}
