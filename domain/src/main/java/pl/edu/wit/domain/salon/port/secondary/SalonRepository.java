package pl.edu.wit.domain.salon.port.secondary;

import pl.edu.wit.domain.salon.shared.SalonDto;
import pl.edu.wit.domain.salon.shared.SalonId;

public interface SalonRepository {

    SalonId save(SalonDto salon);

}
