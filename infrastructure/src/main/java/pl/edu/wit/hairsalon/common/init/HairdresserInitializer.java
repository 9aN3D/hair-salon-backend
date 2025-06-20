package pl.edu.wit.hairsalon.common.init;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.hairdresser.HairdresserFacade;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;
import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

@Component
public class HairdresserInitializer {

    private final HairdresserFacade hairdresserFacade;
    private final ServiceFacade serviceFacade;

    public HairdresserInitializer(HairdresserFacade hairdresserFacade, ServiceFacade serviceFacade) {
        this.hairdresserFacade = hairdresserFacade;
        this.serviceFacade = serviceFacade;
    }

    public void createIfNecessary() {
        var hairdresserPage = hairdresserFacade.findAll(HairdresserFindQuery.empty(), PageRequest.of(0, 1));
        if (!hairdresserPage.hasContent()) {
            var fileUploadCommandIterator = prepareFileUploadCommandIterator();
            prepareHairdresserCreateCommands()
                    .stream()
                    .map(hairdresserFacade::create)
                    .forEach(hairdresserId -> hairdresserFacade.uploadPhoto(hairdresserId, fileUploadCommandIterator.next()));
        }
    }

    private List<HairdresserCreateCommand> prepareHairdresserCreateCommands() {
        var productNameToProduct = collectProductNameToProductId();
        return List.of(
                HairdresserCreateCommand.builder()
                        .name("Hairdresser")
                        .surname("I")
                        .services(Set.of(
                                productNameToProduct.get("Strzyżenie męskie"),
                                productNameToProduct.get("Strzyżenie dzieci do 12 lat"),
                                productNameToProduct.get("Strzyżenie maszynką"),
                                productNameToProduct.get("Golenie twarzy brzytwą")
                        ))
                        .build(),
                HairdresserCreateCommand.builder()
                        .name("Hairdresser")
                        .surname("II")
                        .services(Set.of(
                                productNameToProduct.get("Strzyżenie męskie"),
                                productNameToProduct.get("Strzyżenie maszynką"),
                                productNameToProduct.get("Strzyżenie włosów i brody"),
                                productNameToProduct.get("Woskowanie uszu i nosa"),
                                productNameToProduct.get("Golenie twarzy brzytwą")
                        ))
                        .build(),
                HairdresserCreateCommand.builder()
                        .name("Hairdresser")
                        .surname("III")
                        .services(Set.of(
                                productNameToProduct.get("Strzyżenie brody"),
                                productNameToProduct.get("Strzyżenie maszynką"),
                                productNameToProduct.get("Strzyżenie włosów i brody"),
                                productNameToProduct.get("Woskowanie uszu i nosa"),
                                productNameToProduct.get("Golenie twarzy brzytwą"),
                                productNameToProduct.get("Tuszowanie siwizny ze strzyżeniem cover")
                        ))
                        .build()
        );
    }

    private Map<String, String> collectProductNameToProductId() {
        return serviceFacade.findAll(ServiceFindQuery.empty(), PageRequest.of(0, 11)).getContent()
                .stream()
                .collect(toMap(ServiceDto::name, ServiceDto::id));
    }

    private Iterator<FileUploadCommand> prepareFileUploadCommandIterator() {
        return List.of(
                FileUploadCommand.builder()
                        .originalFilename("barber_1.jpeg")
                        .size(233632L)
                        .contentType("image/jpeg")
                        .content(getContent("/Users/vkovalchuk/study/dyplom/img/barber_1.jpeg"))
                        .build(),
                FileUploadCommand.builder()
                        .originalFilename("barber_2.jpeg")
                        .size(222758L)
                        .contentType("image/jpeg")
                        .content(getContent("/Users/vkovalchuk/study/dyplom/img/barber_2.jpeg"))
                        .build(),
                FileUploadCommand.builder()
                        .originalFilename("barber_3.jpeg")
                        .size(144085L)
                        .contentType("image/jpeg")
                        .content(getContent("/Users/vkovalchuk/study/dyplom/img/barber_3.jpeg"))
                        .build()
        ).iterator();
    }

    private FileInputStream getContent(String name) {
        try {
            return new FileInputStream(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
