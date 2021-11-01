package pl.edu.wit.hairsalon.common.init;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.service.command.ServiceCreateCommand;
import pl.edu.wit.hairsalon.servicecategory.ServiceCategoryFacade;
import pl.edu.wit.hairsalon.servicecategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.servicecategory.query.ServiceCategoryFindQuery;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.edu.wit.hairsalon.servicecategory.dto.ServiceCategoryStatusDto.ACTIVE;

@Component
@RequiredArgsConstructor
public class ServiceInitializer {

    private final ServiceFacade serviceFacade;
    private final ServiceCategoryFacade serviceCategoryFacade;

    public void createIfNecessary() {
        var serviceCategories = serviceCategoryFacade.findAll(new ServiceCategoryFindQuery(), PageRequest.of(0, 5));
        if (serviceCategories.isEmpty()) {
            createHaircutServices();
            createCareServices();
            createOtherServices();
        }
    }

    private void createHaircutServices() {
        var serviceIds = getHaircutServiceCreateCommands().stream()
                .map(serviceFacade::create)
                .collect(Collectors.toSet());
        serviceCategoryFacade.create(new ServiceCategoryCreateCommand("Strzyżenie", ACTIVE, serviceIds));
    }

    private Set<ServiceCreateCommand> getHaircutServiceCreateCommands() {
        return Set.of(
                new ServiceCreateCommand("Strzyżenie męskie", new BigDecimal("60.00"), 30L),
                new ServiceCreateCommand("Strzyżenie studenckie", new BigDecimal("50.00"), 30L),
                new ServiceCreateCommand("Strzyżenie dzieci do 12 lat", new BigDecimal("50.00"), 30L),
                new ServiceCreateCommand("Strzyżenie brody", new BigDecimal("50.00"), 25L),
                new ServiceCreateCommand("Strzyżenie maszynką", new BigDecimal("25.00"), 35L),
                new ServiceCreateCommand("Strzyżenie włosów i brody", new BigDecimal("40.00"), 40L)
        );
    }

    private void createCareServices() {
        var serviceIds = getCareServiceCreateCommands().stream()
                .map(serviceFacade::create)
                .collect(Collectors.toSet());
        serviceCategoryFacade.create(new ServiceCategoryCreateCommand("Pielęgnacja", ACTIVE, serviceIds));
    }

    private Set<ServiceCreateCommand> getCareServiceCreateCommands() {
        return Set.of(
                new ServiceCreateCommand("Tuszowanie siwizny ze strzyżeniem cover", new BigDecimal("100.00"), 20L),
                new ServiceCreateCommand("Tuszowanie siwizny cover bez strzyżeniem", new BigDecimal("45.00"), 25L),
                new ServiceCreateCommand("Woskowanie uszu i nosa", new BigDecimal("30.00"), 35L)
        );
    }

    private void createOtherServices() {
        var serviceIds = getOtherServiceCreateCommands().stream()
                .map(serviceFacade::create)
                .collect(Collectors.toSet());
        serviceCategoryFacade.create(new ServiceCategoryCreateCommand("Inne", ACTIVE, serviceIds));
    }

    private Set<ServiceCreateCommand> getOtherServiceCreateCommands() {
        return Set.of(
                new ServiceCreateCommand("Golenie twarzy brzytwą", new BigDecimal("20.00"), 20L)
        );
    }

}
