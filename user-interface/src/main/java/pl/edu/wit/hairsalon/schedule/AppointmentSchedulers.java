package pl.edu.wit.hairsalon.schedule;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.appointment.AppointmentFacade;

@Component
class AppointmentSchedulers {

    private final AppointmentFacade appointmentFacade;

    public AppointmentSchedulers(AppointmentFacade appointmentFacade) {
        this.appointmentFacade = appointmentFacade;
    }

    //At every 30th minute
    @Scheduled(cron = "* 30 * * * *")
    void remindAppointments() {
        appointmentFacade.reminds(PageRequest.of(0, 50, Sort.Direction.DESC, "creationDateTime"));
    }

}
