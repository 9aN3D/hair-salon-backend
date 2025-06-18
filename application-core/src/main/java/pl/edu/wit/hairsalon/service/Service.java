package pl.edu.wit.hairsalon.service;

import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.Money;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.time.Duration;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

record Service(
        String id,
        String name,
        Money price,
        Duration duration
) implements SelfValidator<Service> {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Service service)) return false;
        return Objects.equals(id, service.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

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

    ServiceDto toDto() {
        return ServiceDto.builder()
                .id(id)
                .name(name)
                .price(price.toDto())
                .durationInMinutes(duration.toMinutes())
                .build();
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private String id;
        private String name;
        private Money price;
        private Duration duration;

        Builder id(String id) {
            this.id = id;
            return this;
        }

        Builder name(String name) {
            this.name = name;
            return this;
        }

        Builder price(Money price) {
            this.price = price;
            return this;
        }

        Builder duration(Duration duration) {
            this.duration = duration;
            return this;
        }

        Service build() {
            return new Service(id, name, price, duration);
        }

    }

}
