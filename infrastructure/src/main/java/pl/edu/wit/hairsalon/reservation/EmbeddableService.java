package pl.edu.wit.hairsalon.reservation;

import com.querydsl.core.annotations.QueryEmbeddable;
import pl.edu.wit.hairsalon.sharedKernel.document.EmbeddableMoney;

@QueryEmbeddable
record EmbeddableService(
        String id,
        String name,
        EmbeddableMoney price,
        Long durationInMinutes
) {

}
