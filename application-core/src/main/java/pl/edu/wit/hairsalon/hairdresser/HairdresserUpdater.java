package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.hairdresser.command.HairdresserUpdateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.FullName;

import java.util.Set;

import static java.util.Optional.ofNullable;
import static pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery.ofHairdresserId;

class HairdresserUpdater {

    private final HairdresserPort hairdresserPort;

    HairdresserUpdater(HairdresserPort hairdresserPort) {
        this.hairdresserPort = hairdresserPort;
    }

    HairdresserDto update(String hairdresserId, HairdresserUpdateCommand command) {
        var hairdresserDto = hairdresserPort.findOneOrThrow(ofHairdresserId(hairdresserId));
        var updatedHairdresser = buildHairdresser(hairdresserDto, command).validate();
        return hairdresserPort.save(updatedHairdresser.toDto());
    }

    private Hairdresser buildHairdresser(HairdresserDto dto, HairdresserUpdateCommand command) {
        return Hairdresser.builder()
                .id(dto.id())
                .fullName(getFullName(dto, command))
                .services(getServices(dto, command))
                .build();
    }

    private FullName getFullName(HairdresserDto dto, HairdresserUpdateCommand command) {
        return new FullName(
                ofNullable(command.name()).orElseGet(() -> dto.fullName().name()),
                ofNullable(command.surname()).orElseGet(() -> dto.fullName().surname())
        );
    }

    private Set<String> getServices(HairdresserDto dto, HairdresserUpdateCommand command) {
        return ofNullable(command.services())
                .orElseGet(dto::serviceIds);
    }

}
