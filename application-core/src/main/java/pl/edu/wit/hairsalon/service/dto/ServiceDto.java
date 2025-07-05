package pl.edu.wit.hairsalon.service.dto;

import pl.edu.wit.hairsalon.sharedKernel.dto.MoneyDto;

import java.time.Duration;
import java.util.Objects;
import java.util.StringJoiner;

public record ServiceDto(
        String id,
        String name,
        MoneyDto price,
        Long durationInMinutes
) {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ServiceDto that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Duration getDurationInMinutes() {
        return Duration.ofMinutes(durationInMinutes);
    }
    
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String name;
        private MoneyDto price;
        private Long durationInMinutes;

        Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(MoneyDto price) {
            this.price = price;
            return this;
        }

        public Builder durationInMinutes(Long durationInMinutes) {
            this.durationInMinutes = durationInMinutes;
            return this;
        }

        public ServiceDto build() {
            return new ServiceDto(this.id, this.name, this.price, this.durationInMinutes);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Builder.class.getSimpleName() + "[", "]")
                    .add("id='" + id + "'")
                    .add("name='" + name + "'")
                    .add("price=" + price)
                    .add("durationInMinutes=" + durationInMinutes)
                    .toString();
        }

    }

}
