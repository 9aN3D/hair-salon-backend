package pl.edu.wit.hairsalon.web.adapter;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.service.command.ServiceCreateCommand;
import pl.edu.wit.hairsalon.service.command.ServiceUpdateCommand;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;
import pl.edu.wit.hairsalon.web.response.PagedResponse;
import pl.edu.wit.hairsalon.web.response.ServiceResponse;

/**
 * Adapter odpowiedzialny za operacje związane z usługami świadczonymi przez salon.
 * <p>
 * Integruje się z warstwą domenową {@link ServiceFacade}, zapewniając operacje tworzenia,
 * aktualizacji oraz odczytu usług w formacie gotowym do użycia w warstwie REST.
 * </p>
 *
 * <p>Zakres funkcjonalny:</p>
 * <ul>
 *     <li>Tworzenie nowej usługi,</li>
 *     <li>Aktualizacja istniejącej usługi,</li>
 *     <li>Wyszukiwanie pojedynczej lub paginowanej listy usług.</li>
 * </ul>
 *
 * @see ServiceResponse
 * @see ServiceFacade
 */
@Service
public class ServiceResponseAdapter {

    private final ServiceFacade serviceFacade;

    /**
     * Tworzy instancję adaptera dla usług.
     *
     * @param serviceFacade fasada odpowiedzialna za logikę domenową usług
     */
    public ServiceResponseAdapter(ServiceFacade serviceFacade) {
        this.serviceFacade = serviceFacade;
    }

    /**
     * Tworzy nową usługę na podstawie danych wejściowych.
     *
     * @param command komenda zawierająca dane nowej usługi
     */
    public void create(ServiceCreateCommand command) {
        serviceFacade.create(command);
    }

    /**
     * Aktualizuje istniejącą usługę.
     *
     * @param serviceId identyfikator usługi
     * @param command   komenda zawierająca dane do aktualizacji
     */
    public void update(String serviceId, ServiceUpdateCommand command) {
        serviceFacade.update(serviceId, command);
    }

    /**
     * Zwraca jedną usługę na podstawie jej identyfikatora.
     *
     * @param serviceId identyfikator usługi
     * @return szczegóły usługi w formacie odpowiedzi REST
     */
    public ServiceResponse findOne(String serviceId) {
        return ServiceResponse.of(serviceFacade.findOne(serviceId));
    }

    /**
     * Zwraca paginowaną listę usług spełniających kryteria zapytania.
     *
     * @param findQuery zapytanie filtrujące
     * @param pageable  dane paginacyjne
     * @return paginowana lista usług
     */
    public PagedResponse<ServiceResponse> findAll(ServiceFindQuery findQuery, Pageable pageable) {
        return PagedResponse.from(
                serviceFacade.findAll(findQuery, pageable)
                        .map(ServiceResponse::of)
        );
    }

}
