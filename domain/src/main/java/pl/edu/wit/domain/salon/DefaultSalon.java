package pl.edu.wit.domain.salon;

import pl.edu.wit.domain.Entity;
import pl.edu.wit.domain.IdGenerator;
import pl.edu.wit.domain.salon.shared.CreateSalonCommand;
import pl.edu.wit.domain.salon.shared.CreateSalonResult;
import pl.edu.wit.domain.salon.shared.SalonDto;
import pl.edu.wit.domain.salon.shared.SalonId;

class DefaultSalon extends Entity<SalonId> implements Salon {

    private final String name;

    public DefaultSalon(IdGenerator idGenerator, String name) {
        super(new SalonId(idGenerator));
        this.name = name;
    }

    public DefaultSalon(IdGenerator idGenerator, CreateSalonCommand command) {
        this(idGenerator, command.getName());
    }

    @Override
    public SalonDto toDto() {
        return SalonDto.builder()
                .salonId(id())
                .name(name)
                .build();
    }

    @Override
    public CreateSalonResult result() {
        return new CreateSalonResult(id());
    }

}
