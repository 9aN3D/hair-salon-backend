package pl.edu.wit.hairsalon.web.adapter;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.reservation.ReservationFacade;
import pl.edu.wit.hairsalon.reservation.command.ReservationCalculateCommand;
import pl.edu.wit.hairsalon.reservation.command.ReservationMakeCommand;
import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculationDto;
import pl.edu.wit.hairsalon.reservation.dto.ReservationHairdresserDto;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.serviceCategory.ServiceCategoryFacade;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.serviceCategory.query.ServiceCategoryFindQuery;
import pl.edu.wit.hairsalon.uploadableFile.UploadableFileFacade;
import pl.edu.wit.hairsalon.web.response.HairdresserResponse;
import pl.edu.wit.hairsalon.web.response.ReservationCalculationResponse;
import pl.edu.wit.hairsalon.web.response.ServiceResponse;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static pl.edu.wit.hairsalon.sharedKernel.CollectionHelper.isNullOrEmpty;

/**
 * Adapter odpowiedzialny za przetwarzanie i udostępnianie informacji dotyczących rezerwacji usług w salonie.
 * <p>
 * Integruje logikę z warstwy domenowej {@link ReservationFacade}, {@link ServiceCategoryFacade} i {@link UploadableFileFacade},
 * przekształcając dane domenowe na formaty gotowe do zwrócenia w kontrolerze REST.
 * </p>
 *
 * <p>Główne odpowiedzialności:</p>
 * <ul>
 *     <li>Tworzenie rezerwacji przez klienta,</li>
 *     <li>Obliczanie możliwych opcji rezerwacji (dostępne godziny, usługi, fryzjerzy),</li>
 *     <li>Ubogacanie danych o usługi z informacjami o kategoriach i zdjęciach.</li>
 * </ul>
 *
 * @see ReservationCalculationResponse
 * @see HairdresserResponse
 * @see ServiceResponse
 */

@Service
public class ReservationResponseAdapter {

    private final ReservationFacade reservationFacade;
    private final ServiceCategoryFacade serviceCategoryFacade;
    private final UploadableFileFacade uploadableFileFacade;

    /**
     * Tworzy adapter rezerwacji na podstawie fasad domenowych.
     */
    public ReservationResponseAdapter(ReservationFacade reservationFacade,
                                      ServiceCategoryFacade serviceCategoryFacade,
                                      UploadableFileFacade uploadableFileFacade) {
        this.reservationFacade = reservationFacade;
        this.serviceCategoryFacade = serviceCategoryFacade;
        this.uploadableFileFacade = uploadableFileFacade;
    }

    /**
     * Tworzy nową rezerwację na podstawie danych użytkownika i komendy.
     *
     * @param memberId identyfikator klienta wykonującego rezerwację
     * @param command  dane wejściowe rezerwacji (np. wybrane usługi, fryzjer, czas)
     * 
     * @see ReservationMakeCommand
     */
    public void make(String memberId, ReservationMakeCommand command) {
        reservationFacade.make(memberId, command);
    }
    /**
     * Oblicza dostępność usług, fryzjerów i godzin na podstawie podanych danych rezerwacyjnych.
     * <p>
     * Wynik zawiera szczegółowe informacje, takie jak możliwe godziny rozpoczęcia, sumaryczną cenę,
     * dostępnych fryzjerów (z obsługiwanymi usługami) i dostępne usługi z przypisanymi kategoriami.
     * </p>
     *
     * @param memberId identyfikator klienta
     * @param command  dane wejściowe (np. data, preferencje usługowe)
     * @return obiekt z obliczoną propozycją rezerwacji
     * 
     * @see ReservationCalculateCommand
     * @see ReservationCalculationResponse
     */
    public ReservationCalculationResponse calculate(String memberId, ReservationCalculateCommand command) {
        var reservationCalculation = reservationFacade.calculate(memberId, command);
        var serviceIdToServiceResponse = collectServiceIdToServiceResponse(reservationCalculation.availableServices());

        return ReservationCalculationResponse.builder()
                .memberId(reservationCalculation.memberId())
                .calculationTime(reservationCalculation.calculationTime())
                .date(reservationCalculation.date())
                .availableServices(collectServiceResponses(reservationCalculation.availableServices(), serviceIdToServiceResponse))
                .selectedServices(collectServiceResponses(reservationCalculation.selectedServices(), serviceIdToServiceResponse))
                .totalPrice(reservationCalculation.totalPrice())
                .availableHairdressers(prepareHairdressers(reservationCalculation.availableHairdressers(), serviceIdToServiceResponse.values()))
                .selectedHairdresser(prepareHairdresser(reservationCalculation, serviceIdToServiceResponse.values()))
                .availableStartTimes(reservationCalculation.formatAvailableStartTimes())
                .times(reservationCalculation.times().orElse(null))
                .build();
    }

    /**
     * Tworzy listę odpowiedzi dla fryzjerów z przypisanymi usługami i zdjęciem profilowym.
     */
    private List<HairdresserResponse> prepareHairdressers(List<ReservationHairdresserDto> availableHairdressers, Collection<ServiceResponse> values) {
        return availableHairdressers.stream()
                .map(hairdresser -> HairdresserResponse.of(hairdresser, values, uploadableFileFacade::findOne))
                .toList();
    }

    /**
     * Tworzy odpowiedź dla wybranego fryzjera, jeśli został podany.
     */
    private HairdresserResponse prepareHairdresser(ReservationCalculationDto reservationCalculation, Collection<ServiceResponse> values) {
        return reservationCalculation.selectedHairdresser()
                .map(hairdresser -> HairdresserResponse.of(hairdresser, values, uploadableFileFacade::findOne))
                .orElse(null);
    }

    /**
     * Buduje mapę {@code serviceId -> ServiceResponse}, uwzględniając przypisanie do kategorii.
     */
    private Map<String, ServiceResponse> collectServiceIdToServiceResponse(List<ServiceDto> services) {
        var serviceCategories = getServiceCategories(services);
        return services.stream()
                .map(service -> getServiceCategory(serviceCategories, service.id())
                        .map(category -> ServiceResponse.of(service, category))
                        .orElseGet(() -> ServiceResponse.of(service)))
                .collect(toMap(ServiceResponse::id, identity()));
    }

    /**
     * Przypisuje usługę do jej kategorii (jeśli istnieje).
     */
    private Optional<ServiceCategoryDto> getServiceCategory(List<ServiceCategoryDto> serviceCategories, String serviceId) {
        return serviceCategories.stream()
                .filter(serviceCategory -> serviceCategory.itemIds().contains(serviceId))
                .findFirst();
    }

    /**
     * Pobiera kategorie usług na podstawie ID usług, do których należą.
     */
    private List<ServiceCategoryDto> getServiceCategories(List<ServiceDto> services) {
        var hairdresserServiceIds = collectHairdresserServiceIds(services);
        var findQuery = ServiceCategoryFindQuery.withServiceIds(hairdresserServiceIds);
        var pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        return serviceCategoryFacade.findAll(findQuery, pageRequest).getContent();
    }

    /**
     * Zbiera unikalne identyfikatory usług fryzjerskich.
     */
    private Set<String> collectHairdresserServiceIds(List<ServiceDto> hairdresserServices) {
        return hairdresserServices.stream()
                .map(ServiceDto::id)
                .collect(toSet());
    }

    /**
     * Tworzy listę odpowiedzi usług na podstawie mapy ID -> odpowiedź.
     */
    private List<ServiceResponse> collectServiceResponses(List<ServiceDto> services, Map<String, ServiceResponse> serviceIdToServiceResponse) {
        if (isNullOrEmpty(services)) {
            return null;
        }
        return services.stream()
                .map(ServiceDto::id)
                .map(serviceIdToServiceResponse::get)
                .filter(Objects::nonNull)
                .collect(toList());

    }

}
