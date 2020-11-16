package pl.edu.wit.domain.salon.shared;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalonDto {

    private SalonId salonId;

    private String name;

}
