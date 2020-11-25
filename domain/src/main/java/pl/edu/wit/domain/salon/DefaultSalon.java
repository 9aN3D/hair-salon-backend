package pl.edu.wit.domain.salon;

import pl.edu.wit.domain.Entity;
import pl.edu.wit.domain.IdGenerator;
import pl.edu.wit.domain.common.StringNotBlank;
import pl.edu.wit.domain.salon.shared.CreateSalonCommand;
import pl.edu.wit.domain.salon.shared.CreateSalonResult;
import pl.edu.wit.domain.salon.shared.SalonDto;
import pl.edu.wit.domain.salon.shared.SalonId;

import java.util.Objects;

class DefaultSalon extends Entity<SalonId> implements Salon {

    private final String name;
    private final SalonAddress address;

    public DefaultSalon(IdGenerator idGenerator, String name, SalonAddress address) {
        super(new SalonId(idGenerator));
        this.name = new StringNotBlank(name).validate();
        this.address = Objects.requireNonNull(address, "Salon address cannot be null");
    }

    public DefaultSalon(IdGenerator idGenerator, CreateSalonCommand command) {
        this(idGenerator, command.getName(), new SalonAddress(command.getAddress()));
    }

    @Override
    public SalonDto toDto() {
        return SalonDto.builder()
                .salonId(id())
                .name(name)
                .address(address.toDto())
                .build();
    }

    @Override
    public CreateSalonResult result() {
        return new CreateSalonResult(id());
    }

}
