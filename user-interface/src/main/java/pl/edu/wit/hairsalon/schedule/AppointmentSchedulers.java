package pl.edu.wit.hairsalon.schedule;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.appointment.AppointmentFacade;

/**
 * Komponent odpowiedzialny za okresowe przypomnienia o nadchodzących spotkaniach.
 * <p>
 * Wykorzystuje mechanizm planowania z {@link Scheduled}, aby co godzinę o 30. minucie
 * wywoływać metodę przypominającą użytkownikom o zaplanowanych wizytach.
 * </p>
 *
 * <p>Deleguje logikę przypomnień do fasady {@link AppointmentFacade}.</p>
 *
 * @see AppointmentFacade#reminds(org.springframework.data.domain.Pageable)
 */
@Component
class AppointmentSchedulers {

    private final AppointmentFacade appointmentFacade;

    /**
     * Tworzy komponent harmonogramu przypomnień spotkań.
     *
     * @param appointmentFacade fasada zarządzająca spotkaniami
     */
    public AppointmentSchedulers(AppointmentFacade appointmentFacade) {
        this.appointmentFacade = appointmentFacade;
    }

    /**
     * Wywoływana automatycznie co godzinę o 30. minucie.
     * <p>
     * Pobiera pierwsze 50 najnowszych spotkań (według daty utworzenia) i przekazuje je
     * do fasady w celu wysłania przypomnień.
     * </p>
     */
    @Scheduled(cron = "0 30 * * * *")
    void remindAppointments() {
        appointmentFacade.reminds(PageRequest.of(0, 50, Sort.Direction.DESC, "creationDateTime"));
    }

}
