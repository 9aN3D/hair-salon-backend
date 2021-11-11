package pl.edu.wit.hairsalon.hairdresser;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserUpdateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;

import java.util.Set;

import static java.util.Optional.ofNullable;
import static pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery.ofHairdresserId;

@RequiredArgsConstructor
class HairdresserUpdater {

    private final HairdresserPort hairdresserPort;

    HairdresserDto update(String hairdresserId, HairdresserUpdateCommand command) {
        var hairdresserDto = hairdresserPort.findOneOrThrow(ofHairdresserId(hairdresserId));
        var updatedHairdresser = buildHairdresser(hairdresserDto, command).validate();
        return hairdresserPort.save(updatedHairdresser.toDto());
    }

    private Hairdresser buildHairdresser(HairdresserDto dto, HairdresserUpdateCommand command) {
        return Hairdresser.builder()
                .id(dto.getId())
                .fullName(getFullName(dto, command))
                .services(getServices(dto, command))
                .build();
    }

    private HairdresserFullName getFullName(HairdresserDto dto, HairdresserUpdateCommand command) {
        return HairdresserFullName.builder()
                .name(ofNullable(command.getName())
                        .orElseGet(() -> dto.getFullName().getName()))
                .surname(ofNullable(command.getSurname())
                        .orElseGet(() -> dto.getFullName().getSurname()))
                .build();
    }

    private Set<String> getServices(HairdresserDto dto, HairdresserUpdateCommand command) {
        return ofNullable(command.getServices())
                .orElseGet(dto::getServiceIds);
    }

}
