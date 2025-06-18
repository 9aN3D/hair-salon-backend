package pl.edu.wit.hairsalon.service.dto;

import pl.edu.wit.hairsalon.sharedKernel.dto.MoneyDto;

import java.util.StringJoiner;

public record ServiceDto(
        String id,
        String name,
        MoneyDto price,
        Long durationInMinutes
) {

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
