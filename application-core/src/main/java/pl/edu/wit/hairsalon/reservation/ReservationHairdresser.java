package pl.edu.wit.hairsalon.reservation;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.reservation.dto.ReservationHairdresserDto;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "hairdresserId")
class ReservationHairdresser implements SelfValidator<ReservationHairdresser> {

    private final String hairdresserId;
    private final ReservationHairdresserFullName fullName;
    private final String photoId;
    private final List<ReservationHairdresserService> services = new ArrayList<>();

    ReservationHairdresser(HairdresserDto arg) {
        this(
                arg.getId(),
                new ReservationHairdresserFullName(arg.getFullName()),
                arg.getPhotoId()
        );
    }

    ReservationHairdresser(ReservationHairdresserDto arg) {
        this(
                arg.getId(),
                new ReservationHairdresserFullName(arg.getFullName()),
                arg.getPhotoId()
        );
        addAllServices(arg.getServices());
    }

    String id() {
        return hairdresserId;
    }

    ReservationHairdresser addAllServices(List<ServiceDto> args) {
        requireNonNull(args, "Reservation hairdresser services must not null");
        services.addAll(args.stream()
                .map(ReservationHairdresserService::new)
                .collect(toList())
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
        if (services.size() == 0) {
            throw new ValidationException("Reservation hairdresser services size must not be equal zero");
        }
        return this;
    }

}
