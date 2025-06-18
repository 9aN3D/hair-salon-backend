package pl.edu.wit.hairsalon.web.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.serviceCategory.ServiceCategoryFacade;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.serviceCategory.query.ServiceCategoryFindQuery;
import pl.edu.wit.hairsalon.web.response.ServiceCategoryResponse;
import pl.edu.wit.hairsalon.web.response.ServiceResponse;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import static java.lang.Integer.MAX_VALUE;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static pl.edu.wit.hairsalon.service.query.ServiceFindQuery.withIds;

@Service
public class ServiceCategoryResponseAdapter {

    private final ServiceCategoryFacade serviceCategoryFacade;
    private final ServiceFacade serviceFacade;

    public ServiceCategoryResponseAdapter(ServiceCategoryFacade serviceCategoryFacade, ServiceFacade serviceFacade) {
        this.serviceCategoryFacade = serviceCategoryFacade;
        this.serviceFacade = serviceFacade;
    }

    public void create(ServiceCategoryCreateCommand command) {
        serviceCategoryFacade.create(command);
    }

    public void update(String productCategoryId, ServiceCategoryUpdateCommand command) {
        serviceCategoryFacade.update(productCategoryId, command);
    }

    public Page<ServiceCategoryResponse> findAll(ServiceCategoryFindQuery findQuery, Pageable pageable) {
        var serviceCategoryPage = serviceCategoryFacade.findAll(findQuery, pageable);
        return ifServiceCategoryHasContentPrepareResponse(serviceCategoryPage);
    }

    private Page<ServiceCategoryResponse> ifServiceCategoryHasContentPrepareResponse(Page<ServiceCategoryDto> serviceCategoryPage) {
        if (serviceCategoryPage.hasContent()) {
            var serviceIds = collectServiceIds(serviceCategoryPage);
            var servicePage = serviceFacade.findAll(withIds(serviceIds), PageRequest.of(0, MAX_VALUE))
                    .map(ServiceResponse::of);
            if (servicePage.hasContent()) {
                var serviceCategoryIdToServices = collectServiceIdToService(servicePage);

                return serviceCategoryPage
                        .map(serviceCategory -> toServiceCategoryResponse(serviceCategory, serviceCategoryIdToServices));
            }
        }
        return serviceCategoryPage.map(ServiceCategoryResponse::of);
    }

    private Set<String> collectServiceIds(Page<ServiceCategoryDto> serviceCategoryPage) {
        return serviceCategoryPage.getContent()
                .stream()
                .map(ServiceCategoryDto::itemIds)
                .flatMap(Collection::stream)
                .collect(toSet());
    }

    private Map<String, ServiceResponse> collectServiceIdToService(Page<ServiceResponse> productPage) {
        return productPage.getContent()
                .stream()
                .collect(toMap(ServiceResponse::id, Function.identity()));
    }

    private ServiceCategoryResponse toServiceCategoryResponse(ServiceCategoryDto serviceCategory, Map<String, ServiceResponse> serviceCategoryIdToServices) {
        var serviceCategoryResponse = ServiceCategoryResponse.of(serviceCategory);
        serviceCategoryResponse.addServices(serviceCategory.itemIds()
                .stream()
                .map(serviceCategoryIdToServices::get)
                .filter(Objects::nonNull)
                .collect(toSet()));
        return serviceCategoryResponse;
    }

}
