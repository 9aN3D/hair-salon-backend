package pl.edu.wit.hairsalon.service;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.Money;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
class Service implements SelfValidator<Service> {

    private final String id;
    private final String name;
    private final Money price;
    private final Duration duration;

    @Override
    public Service validate() {
        validate(new NotBlankString(id), new NotBlankString(name));
        requireNonNull(price, "Service price must not be null");
        requireNonNull(duration, "Service duration must not be null");
        if (price.hasNotPositiveAmount()) {
            throw new ValidationException("Service price must not be negative");
        }
        if (duration.isNegative()) {
            throw new ValidationException("Service duration must not be negative");
        }
        return this;
    }

    public ServiceDto toDto() {
        return ServiceDto.builder()
                .id(id)
                .name(name)
                .price(price.toDto())
                .durationInMinutes(duration.toMinutes())
                .build();
    }

}
