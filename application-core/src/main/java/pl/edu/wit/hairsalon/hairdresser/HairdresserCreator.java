package pl.edu.wit.hairsalon.hairdresser;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

@RequiredArgsConstructor
class HairdresserCreator {

    private final IdGenerator idGenerator;
    private final HairdresserPort hairdresserPort;

    HairdresserDto create(HairdresserCreateCommand command) {
        var newHairdresser = buildHairdresser(command).validate();
        return hairdresserPort.save(newHairdresser.toDto());
    }

    private Hairdresser buildHairdresser(HairdresserCreateCommand command) {
        return Hairdresser.builder()
                .id(idGenerator.generate())
                .fullName(new HairdresserFullName(command.getName(), command.getSurname()))
                .services(command.getServices())
                .build();
    }

}
