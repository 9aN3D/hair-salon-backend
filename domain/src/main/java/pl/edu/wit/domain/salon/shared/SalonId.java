package pl.edu.wit.domain.salon.shared;

import lombok.Value;
import pl.edu.wit.domain.IdGenerator;

import static java.util.Objects.isNull;

@Value
public class SalonId {

    String id;

    public SalonId(IdGenerator id) {
        if (isNull(id)) {
            throw new IllegalArgumentException("Salon id generator cannot be null");
        }
        this.id = id.generate();
    }

}
