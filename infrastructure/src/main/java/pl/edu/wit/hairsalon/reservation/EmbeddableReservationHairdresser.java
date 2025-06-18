package pl.edu.wit.hairsalon.reservation;

import com.querydsl.core.annotations.QueryEmbeddable;
import pl.edu.wit.hairsalon.sharedKernel.document.EmbeddableFullName;

import java.util.List;

@QueryEmbeddable
record EmbeddableReservationHairdresser(
        String id,
        EmbeddableFullName fullName,
        String photoId,
        List<EmbeddableService> services
) {

}
