package pl.edu.wit.domain.salon;

import org.junit.jupiter.api.Test;
import pl.edu.wit.domain.base.TestGenerator;
import pl.edu.wit.domain.salon.shared.SalonId;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateSalonHandlerTest extends SalonBaseTest {

    @Test
    void shouldCreateSalonWhenHandleCommand() {
        var idGenerator = new TestGenerator();
        var createSalonHandler = new SalonHandlerFacade(idGenerator, new InMemorySalonRepository()).getCreateSalonHandler();
        var createSalonCommand = buildCreateSalonCommand();

        var result = createSalonHandler.handle(createSalonCommand);

        assertEquals(new SalonId(idGenerator), result.getSalonId());
    }

}