package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.FullName;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

class HairdresserCreator {

    private final IdGenerator idGenerator;
    private final HairdresserPort hairdresserPort;

    HairdresserCreator(IdGenerator idGenerator, HairdresserPort hairdresserPort) {
        this.idGenerator = idGenerator;
        this.hairdresserPort = hairdresserPort;
    }

    HairdresserDto create(HairdresserCreateCommand command) {
        var newHairdresser = buildHairdresser(command).validate();
        return hairdresserPort.save(newHairdresser.toDto());
    }

    private Hairdresser buildHairdresser(HairdresserCreateCommand command) {
        return Hairdresser.builder()
                .id(idGenerator.generate())
                .fullName(new FullName(command.name(), command.surname()))
                .services(command.services())
                .build();
    }

}
