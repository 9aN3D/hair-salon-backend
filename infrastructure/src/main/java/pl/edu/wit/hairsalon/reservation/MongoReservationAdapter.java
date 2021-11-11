package pl.edu.wit.hairsalon.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.reservation.dto.ReservationDto;
import pl.edu.wit.hairsalon.reservation.exception.ReservationNotFoundException;

import static java.lang.String.format;

@Repository
@RequiredArgsConstructor
class MongoReservationAdapter implements ReservationPort {

    private final MongoReservationRepository repository;
    private final ReservationMapper mapper;

    @Override
    public ReservationDto findOneOrThrow(String reservationId) {
        return repository.findById(reservationId)
                .map(mapper::toDto)
                .orElseThrow(() -> new ReservationNotFoundException(
                        format("Reservation not found by id: %s", reservationId)
                ));
    }

}
