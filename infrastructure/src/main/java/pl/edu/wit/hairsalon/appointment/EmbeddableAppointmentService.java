package pl.edu.wit.hairsalon.appointment;

import com.querydsl.core.annotations.QueryEmbeddable;
import pl.edu.wit.hairsalon.sharedKernel.dto.MoneyDto;

@QueryEmbeddable
record EmbeddableAppointmentService(
        String serviceId,
        String name,
        MoneyDto price,
        Long durationInMinutes
) {

}
