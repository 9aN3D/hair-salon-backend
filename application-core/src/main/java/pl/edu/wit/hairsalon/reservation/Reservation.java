package pl.edu.wit.hairsalon.reservation;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.sharedkernel.SelfValidator;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
class Reservation implements SelfValidator<Reservation> {

    private final String id;

    @Override
    public Reservation validate() {
        return this;
    }

}
