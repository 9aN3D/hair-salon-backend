package pl.edu.wit.hairsalon.eventbus;

import pl.edu.wit.hairsalon.reservation.event.ReservationMadeEvent;

interface ReservationMadeEventHandler {

    void handle(ReservationMadeEvent event);

}
