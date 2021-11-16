package pl.edu.wit.hairsalon.eventBus;

import pl.edu.wit.hairsalon.reservation.event.ReservationMadeEvent;

interface ReservationMadeEventHandler {

    void handle(ReservationMadeEvent event);

}
