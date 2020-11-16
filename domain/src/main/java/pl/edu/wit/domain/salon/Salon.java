package pl.edu.wit.domain.salon;

import pl.edu.wit.domain.salon.shared.CreateSalonResult;
import pl.edu.wit.domain.salon.shared.SalonDto;

interface Salon {

    SalonDto toDto();

    CreateSalonResult result();

}
