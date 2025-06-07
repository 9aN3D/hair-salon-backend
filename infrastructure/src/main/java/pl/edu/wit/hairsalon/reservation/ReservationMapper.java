package pl.edu.wit.hairsalon.reservation;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.reservation.dto.ReservationDto;

@Component
@Mapper(builder = @Builder(disableBuilder = true),componentModel = "spring")
abstract class ReservationMapper {

    abstract ReservationDto toDto(ReservationDocument reservationDocument);

    abstract ReservationDocument toDocument(ReservationDto reservationDto);

}
