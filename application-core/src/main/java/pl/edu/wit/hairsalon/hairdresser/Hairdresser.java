package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.FullName;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

record Hairdresser(
        String id,
        FullName fullName,
        String photoId,
        Set<String> services,
        HairdresserWeeklySchedule weeklySchedule
) implements SelfValidator<Hairdresser> {

    public Hairdresser(HairdresserDto dto) {
        this(
                dto.id(),
                new FullName(dto.fullName()),
                dto.photoId(),
                dto.serviceIds(),
                new HairdresserWeeklySchedule(dto.weeklySchedule())
        );
    }

    @Override
    public Hairdresser validate() {
        requireNonNull(services, "Hairdresser services must not be null");
        requireNonNull(weeklySchedule, "Hairdresser weeklySchedule must not be null");
        validate(new NotBlankString(id), fullName, weeklySchedule);
        return this;
    }

    public HairdresserDto toDto() {
        return HairdresserDto.builder()
                .id(id)
                .fullName(fullName.toDto())
                .photoId(photoId)
                .serviceIds(services)
                .weeklySchedule(weeklySchedule.toDtoMap())
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
                .weeklySchedule(weeklySchedule)
                .build();
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private String id;
        private FullName fullName;
        private String photoId;
        private Set<String> services;
        private HairdresserWeeklySchedule weeklySchedule;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder fullName(FullName fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder photoId(String photoId) {
            this.photoId = photoId;
            return this;
        }

        public Builder services(Set<String> services) {
            this.services = services;
            return this;
        }

        public Builder weeklySchedule(HairdresserWeeklySchedule weeklySchedule) {
            this.weeklySchedule = weeklySchedule;
            return this;
        }

        public Hairdresser build() {
            return new Hairdresser(id, fullName, photoId, services, weeklySchedule);
        }

    }

}
