package pl.edu.wit.hairsalon.appointment;

import com.querydsl.core.annotations.QueryEmbeddable;

import java.math.BigDecimal;
import java.util.List;

@QueryEmbeddable
record EmbeddableAppointmentServices(
        String name,
        Long durationInMinutes,
        BigDecimal price,
        List<EmbeddableAppointmentService> services
) {

}
