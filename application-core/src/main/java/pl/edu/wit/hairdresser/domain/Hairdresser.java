package pl.edu.wit.hairdresser.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.hairdresser.command.HairdresserUpdateCommand;
import pl.edu.wit.common.domain.NotBlankString;
import pl.edu.wit.hairdresser.dto.HairdresserDto;
import pl.edu.wit.common.exception.ValidationException;

import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class Hairdresser {

    private final String id;
    private final String name;
    private final String surname;
    private final String photoId;
    private final Set<String> services;

    public Hairdresser(String id,
                       String name,
                       String surname,
                       String photoId,
                       Set<String> services) {
        this.id = new NotBlankString(id).value();
        this.name = new NotBlankString(name).value();
        this.surname = new NotBlankString(surname).value();
        this.photoId = photoId;
        this.services = ofNullable(services)
                .filter(not(Set::isEmpty))
                .orElseThrow(() -> new ValidationException("Hairdresser services cannot be null or empty"));
    }

    public Hairdresser(HairdresserDto dto) {
        this(
                dto.getId(),
                dto.getName(),
                dto.getSurname(),
                dto.getPhotoId(),
                dto.getServices()
        );
    }

    public HairdresserDto toDto() {
        return HairdresserDto.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .photoId(photoId)
                .services(services)
                .build();
    }

    public String phoneId() {
        return photoId;
    }

    public Boolean hasPhotoId() {
        return nonNull(photoId) && !photoId.isEmpty();
    }

    public Hairdresser update(HairdresserUpdateCommand command) {
        return Hairdresser.builder()
                .id(id)
                .name(ofNullable(command.getName()).orElse(name))
                .surname(ofNullable(command.getSurname()).orElse(surname))
                .photoId(ofNullable(command.getPhotoId()).orElse(photoId))
                .services(ofNullable(command.getServices()).orElse(services))
                .build();
    }

}
