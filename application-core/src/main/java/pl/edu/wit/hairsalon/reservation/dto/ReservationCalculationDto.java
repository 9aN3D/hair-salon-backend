package pl.edu.wit.hairsalon.reservation.dto;

import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.math.BigDecimal;
import java.util.List;

public record ReservationCalculationDto(
        ReservationHairdresserDto hairdresser,
        DateRangeDto times,
        List<ServiceDto> selectedServices,
        BigDecimal totalPrice
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private ReservationHairdresserDto hairdresser;
        private DateRangeDto times;
        private List<ServiceDto> selectedServices;
        private BigDecimal totalPrice;

        public Builder hairdresser(ReservationHairdresserDto hairdresser) {
            this.hairdresser = hairdresser;
            return this;
        }

        public Builder times(DateRangeDto times) {
            this.times = times;
            return this;
        }

        public Builder selectedServices(List<ServiceDto> selectedServices) {
            this.selectedServices = selectedServices;
            return this;
        }

        public Builder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public ReservationCalculationDto build() {
            return new ReservationCalculationDto(hairdresser, times, selectedServices, totalPrice);
        }
    }
    
}
