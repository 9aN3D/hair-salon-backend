package pl.edu.wit.hairsalon.reservation;

import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.reservation.dto.ReservationHairdresserDto;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.FullName;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

record ReservationHairdresser(
        String hairdresserId,
        FullName fullName,
        String photoId,
        List<ReservationHairdresserService> services
) implements SelfValidator<ReservationHairdresser> {

    ReservationHairdresser(String hairdresserId, FullName fullName, String photoId) {
        this(hairdresserId, fullName, photoId, new ArrayList<>());
    }

    ReservationHairdresser(HairdresserDto arg) {
        this(
                arg.id(),
                new FullName(arg.fullName()),
                arg.photoId()
        );
    }

    ReservationHairdresser(ReservationHairdresserDto arg) {
        this(
                arg.id(),
                new FullName(arg.fullName()),
                arg.photoId()
        );
        addAllServices(arg.services());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReservationHairdresser that)) return false;
        return Objects.equals(hairdresserId, that.hairdresserId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hairdresserId);
    }

    String id() {
        return hairdresserId;
    }

    ReservationHairdresser addAllServices(List<ServiceDto> args) {
        requireNonNull(args, "Reservation hairdresser services must not null");
        services.addAll(args.stream()
                .map(ReservationHairdresserService::new)
                .toList()
        );
        return this;
    }

    Map<String, ReservationHairdresserService> collectServiceIdToService() {
        return services.stream()
                .collect(toMap(ReservationHairdresserService::serviceId, identity()));
    }

    ReservationHairdresserDto toDto() {
        return ReservationHairdresserDto.builder()
                .id(hairdresserId)
                .fullName(fullName.toDto())
                .photoId(photoId)
                .services(services.stream()
                        .map(ReservationHairdresserService::toDto)
                        .collect(toList()))
                .build();
    }

    @Override
    public ReservationHairdresser validate() {
        validate(new NotBlankString(hairdresserId), fullName);
        if (services.isEmpty()) {
            throw new ValidationException("Reservation hairdresser services size must not be equal zero");
        }
        return this;
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private String hairdresserId;
        private FullName fullName;
        private String photoId;
        private List<ReservationHairdresserService> services;

        Builder hairdresserId(String hairdresserId) {
            this.hairdresserId = hairdresserId;
            return this;
        }

        Builder fullName(FullName fullName) {
            this.fullName = fullName;
            return this;
        }

        Builder photoId(String photoId) {
            this.photoId = photoId;
            return this;
        }

        Builder services(List<ReservationHairdresserService> services) {
            this.services = services;
            return this;
        }

        ReservationHairdresser build() {
            return new ReservationHairdresser(hairdresserId, fullName, photoId, services);
        }

    }

}
