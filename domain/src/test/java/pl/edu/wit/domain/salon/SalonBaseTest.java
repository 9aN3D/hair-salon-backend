package pl.edu.wit.domain.salon;

import pl.edu.wit.domain.salon.port.secondary.SalonRepository;
import pl.edu.wit.domain.salon.shared.CreateSalonCommand;
import pl.edu.wit.domain.salon.shared.SalonDto;
import pl.edu.wit.domain.salon.shared.SalonId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SalonBaseTest {

    CreateSalonCommand buildCreateSalonCommand() {
        return CreateSalonCommand.builder()
                .name("Salon test")
                .address(CreateSalonCommand.Address.builder()
                        .streetName("Konara 12")
                        .city("Wawa")
                        .postalCode("11-222")
                        .country("PL")
                        .build())
                .build();
    }

    static class InMemorySalonRepository implements SalonRepository {

        private final Map<SalonId, SalonDto> salons = new ConcurrentHashMap<>();

        @Override
        public SalonId save(SalonDto salon) {
            salons.put(salon.getSalonId(), salon);
            return salon.getSalonId();
        }

    }

}
