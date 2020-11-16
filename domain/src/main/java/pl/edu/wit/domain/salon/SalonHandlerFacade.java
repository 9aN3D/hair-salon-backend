package pl.edu.wit.domain.salon;

import lombok.Getter;
import pl.edu.wit.domain.IdGenerator;
import pl.edu.wit.domain.salon.port.primary.CreateSalonHandler;
import pl.edu.wit.domain.salon.port.secondary.SalonRepository;

@Getter
public class SalonHandlerFacade {

    private final CreateSalonHandler createSalonHandler;

    public SalonHandlerFacade(IdGenerator idGenerator, SalonRepository salonRepository) {
        this.createSalonHandler = new DomainCreateSalonHandler(idGenerator, salonRepository);
    }

}
