package pl.edu.wit.hairsalon.reservation;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.hairsalon.sharedkernel.domain.FullName;
import pl.edu.wit.hairsalon.sharedkernel.dto.FullNameDto;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
class ReservationHairdresserFullName extends FullName {

    @Builder
    ReservationHairdresserFullName(String name, String surname) {
        super(name, surname);
    }

    ReservationHairdresserFullName(FullNameDto dto) {
        super(dto);
    }

}
