package pl.edu.wit.hairsalon.appointment;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;

@Component
@Mapper(builder = @Builder(disableBuilder = true),componentModel = "spring")
abstract class AppointmentMapper {

    abstract AppointmentDto toDto(AppointmentDocument appointmentDocument);

    abstract AppointmentDocument toDocument(AppointmentDto appointmentDto);

}
