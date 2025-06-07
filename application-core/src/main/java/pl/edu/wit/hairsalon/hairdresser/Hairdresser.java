package pl.edu.wit.hairsalon.hairdresser;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
class Hairdresser implements SelfValidator<Hairdresser> {

    private final String id;
    private final HairdresserFullName fullName;
    private final String photoId;
    private final Set<String> services;

    public Hairdresser(HairdresserDto dto) {
        this(
                dto.id(),
                new HairdresserFullName(dto.fullName()),
                dto.photoId(),
                dto.serviceIds()
        );
    }

    @Override
    public Hairdresser validate() {
        requireNonNull(services, "Hairdresser services must not be null");
        validate(new NotBlankString(id), fullName);
        return this;
    }

    public HairdresserDto toDto() {
        return HairdresserDto.builder()
                .id(id)
                .fullName(fullName.toDto())
                .photoId(photoId)
                .serviceIds(services)
                .build();
    }

    Hairdresser deletePhoto(Consumer<String> action) {
        if (nonNull(photoId)) {
            action.accept(photoId);
            return copy(photoId);
        }
        return this;
    }

    Hairdresser uploadPhoto(Function<FileUploadCommand, String> action, FileUploadCommand command) {
        var photoId = action.apply(command);
        return copy(photoId);
    }

    public Hairdresser copy(String photoId) {
        return Hairdresser.builder()
                .id(id)
                .fullName(fullName)
                .photoId(photoId)
                .services(services)
                .build();
    }

}
