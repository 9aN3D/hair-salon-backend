package pl.edu.wit.hairsalon.reservation.dto;

import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ReservationDto(
        String id,
        String memberId,
        ReservationHairdresserDto hairdresser,
        DateRangeDto times,
        List<ServiceDto> selectedServices,
        BigDecimal totalPrice,
        LocalDateTime creationDateTime
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String memberId;
        private ReservationHairdresserDto hairdresser;
        private DateRangeDto times;
        private List<ServiceDto> selectedServices;
        private BigDecimal totalPrice;
        private LocalDateTime creationDateTime;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder memberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

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

        public Builder creationDateTime(LocalDateTime creationDateTime) {
            this.creationDateTime = creationDateTime;
            return this;
        }

        public ReservationDto build() {
            return new ReservationDto(id, memberId, hairdresser, times, selectedServices, totalPrice, creationDateTime);
        }
    }
    
}
