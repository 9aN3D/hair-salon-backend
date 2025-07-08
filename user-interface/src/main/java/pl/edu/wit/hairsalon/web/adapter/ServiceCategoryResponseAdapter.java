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
import pl.edu.wit.hairsalon.web.response.PagedResponse;
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

/**
 * Adapter odpowiedzialny za zarządzanie oraz prezentację kategorii usług wraz z przypisanymi usługami.
 * <p>
 * Współpracuje z {@link ServiceCategoryFacade} oraz {@link ServiceFacade}, aby dostarczyć odpowiedzi
 * w formacie {@link ServiceCategoryResponse}, uwzględniając powiązane usługi.
 * </p>
 *
 * <p>Główne funkcje:</p>
 * <ul>
 *     <li>Tworzenie nowej kategorii usług,</li>
 *     <li>Aktualizacja istniejącej kategorii,</li>
 *     <li>Paginowane pobieranie kategorii wraz z listą przypisanych usług.</li>
 * </ul>
 *
 * @see ServiceCategoryResponse
 * @see ServiceCategoryFacade
 * @see ServiceFacade
 */
@Service
public class ServiceCategoryResponseAdapter {

    private final ServiceCategoryFacade serviceCategoryFacade;
    private final ServiceFacade serviceFacade;

    /**
     * Tworzy adapter na podstawie podanych fasad domenowych.
     *
     * @param serviceCategoryFacade fasada do obsługi kategorii usług
     * @param serviceFacade         fasada do obsługi pojedynczych usług
     */
    public ServiceCategoryResponseAdapter(ServiceCategoryFacade serviceCategoryFacade, ServiceFacade serviceFacade) {
        this.serviceCategoryFacade = serviceCategoryFacade;
        this.serviceFacade = serviceFacade;
    }

    /**
     * Tworzy nową kategorię usług.
     *
     * @param command dane wejściowe do utworzenia kategorii
     */
    public void create(ServiceCategoryCreateCommand command) {
        serviceCategoryFacade.create(command);
    }

    /**
     * Aktualizuje istniejącą kategorię usług.
     *
     * @param productCategoryId identyfikator kategorii
     * @param command           dane aktualizacyjne
     */
    public void update(String productCategoryId, ServiceCategoryUpdateCommand command) {
        serviceCategoryFacade.update(productCategoryId, command);
    }

    /**
     * Zwraca paginowaną listę kategorii usług.
     * <p>
     * Jeśli kategorie zawierają przypisane usługi, zostaną one dołączone do odpowiedzi.
     * </p>
     *
     * @param findQuery zapytanie filtrujące
     * @param pageable  dane o paginacji
     * @return odpowiedź z listą kategorii i ewentualnie przypisanymi usługami
     */
    public PagedResponse<ServiceCategoryResponse> findAll(ServiceCategoryFindQuery findQuery, Pageable pageable) {
        var serviceCategoryPage = serviceCategoryFacade.findAll(findQuery, pageable);
        return PagedResponse.from(
                ifServiceCategoryHasContentPrepareResponse(serviceCategoryPage)
        );
    }

    /**
     * Jeżeli kategorie zawierają przypisane usługi, dołącza je do odpowiedzi.
     */
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

    /**
     * Zbiera wszystkie ID usług przypisanych do kategorii.
     */
    private Set<String> collectServiceIds(Page<ServiceCategoryDto> serviceCategoryPage) {
        return serviceCategoryPage.getContent()
                .stream()
                .map(ServiceCategoryDto::itemIds)
                .flatMap(Collection::stream)
                .collect(toSet());
    }

    /**
     * Tworzy mapę ID -> {@link ServiceResponse} dla łatwego dostępu do danych usługi.
     */
    private Map<String, ServiceResponse> collectServiceIdToService(Page<ServiceResponse> productPage) {
        return productPage.getContent()
                .stream()
                .collect(toMap(ServiceResponse::id, Function.identity()));
    }

    /**
     * Buduje {@link ServiceCategoryResponse}, dołączając przypisane usługi.
     */
    private ServiceCategoryResponse toServiceCategoryResponse(ServiceCategoryDto serviceCategory,
                                                              Map<String, ServiceResponse> serviceCategoryIdToServices) {
        var serviceCategoryResponse = ServiceCategoryResponse.of(serviceCategory);
        serviceCategoryResponse.addServices(serviceCategory.itemIds()
                .stream()
                .map(serviceCategoryIdToServices::get)
                .filter(Objects::nonNull)
                .collect(toSet()));
        return serviceCategoryResponse;
    }

}
