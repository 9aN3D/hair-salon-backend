package pl.edu.wit.domain.salon;

import org.junit.jupiter.api.Test;
import pl.edu.wit.domain.IdGenerator;
import pl.edu.wit.domain.salon.port.primary.CreateSalonHandler;
import pl.edu.wit.domain.salon.port.secondary.SalonRepository;
import pl.edu.wit.domain.salon.shared.CreateSalonCommand;
import pl.edu.wit.domain.salon.shared.SalonDto;
import pl.edu.wit.domain.salon.shared.SalonId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateSalonHandlerTest {

    private final CreateSalonHandler createSalonHandler = new SalonHandlerFacade(new TestGenerator(), new InMemorySalonRepository()).getCreateSalonHandler();

    @Test
    void shouldCreateSalon() {
        //given
        var createSalonCommand = CreateSalonCommand.builder()
                .name("Salon test")
                .build();
        //when
        var result = createSalonHandler.handle(createSalonCommand);
        //then
        assertEquals(new SalonId(new TestGenerator()), result.getSalonId());
    }

    private static class TestGenerator implements IdGenerator {

        @Override
        public String generate() {
            return "UUID-uniq-1";
        }

    }

    private static class InMemorySalonRepository implements SalonRepository {

        private final Map<SalonId, SalonDto> salons = new ConcurrentHashMap<>();

        @Override
        public SalonId save(SalonDto salon) {
            salons.put(salon.getSalonId(), salon);
            return salon.getSalonId();
        }

    }

}