package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record ReservationCalculationResponse(
        @NotNull HairdresserResponse hairdresser,
        @NotNull DateRangeDto times,
        List<ServiceResponse> selectedServices,
        BigDecimal totalPrice
) {

    public ReservationCalculationResponse {
        selectedServices = selectedServices != null ? new ArrayList<>(selectedServices) : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationCalculationResponse that)) return false;
        return Objects.equals(hairdresser, that.hairdresser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hairdresser);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private HairdresserResponse hairdresser;
        private DateRangeDto times;
        private List<ServiceResponse> selectedServices;
        private BigDecimal totalPrice;

        public Builder hairdresser(HairdresserResponse hairdresser) {
            this.hairdresser = hairdresser;
            return this;
        }

        public Builder times(DateRangeDto times) {
            this.times = times;
            return this;
        }

        public Builder selectedServices(List<ServiceResponse> selectedServices) {
            this.selectedServices = selectedServices;
            return this;
        }

        public Builder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public ReservationCalculationResponse build() {
            Objects.requireNonNull(hairdresser, "hairdresser must not be null");
            Objects.requireNonNull(times, "times must not be null");
            return new ReservationCalculationResponse(hairdresser, times, selectedServices, totalPrice);
        }

    }

}
