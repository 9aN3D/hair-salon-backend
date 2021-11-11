package pl.edu.wit.hairsalon.web.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.reservation.ReservationFacade;
import pl.edu.wit.hairsalon.reservation.command.ReservationMakeCommand;
import pl.edu.wit.hairsalon.reservation.command.ReservationCalculateCommand;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.servicecategory.ServiceCategoryFacade;
import pl.edu.wit.hairsalon.servicecategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.servicecategory.query.ServiceCategoryFindQuery;
import pl.edu.wit.hairsalon.uploadablefile.UploadableFileFacade;
import pl.edu.wit.hairsalon.web.response.HairdresserResponse;
import pl.edu.wit.hairsalon.web.response.ReservationCalculationResponse;
import pl.edu.wit.hairsalon.web.response.ServiceResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static pl.edu.wit.hairsalon.sharedkernel.CollectionHelper.isNullOrEmpty;

@Service
@RequiredArgsConstructor
public class ReservationResponseAdapter {

    private final ReservationFacade reservationFacade;
    private final ServiceCategoryFacade serviceCategoryFacade;
    private final UploadableFileFacade uploadableFileFacade;

    public void make(String memberId, ReservationMakeCommand command) {
        reservationFacade.make(memberId, command);
    }

    public ReservationCalculationResponse calculate(String memberId, ReservationCalculateCommand command) {
        var reservationCalculation = reservationFacade.calculate(memberId, command);
        var reservationCalculationHairdresser = reservationCalculation.getHairdresser();
        var serviceIdToServiceResponse = collectServiceIdToServiceResponse(reservationCalculationHairdresser.getServices());
        return ReservationCalculationResponse.builder()
                .hairdresser(HairdresserResponse.of(reservationCalculationHairdresser, serviceIdToServiceResponse.values(), uploadableFileFacade::findOne))
                .times(reservationCalculation.getTimes())
                .selectedServices(collectSelectedServiceResponses(reservationCalculation.getSelectedServices(), serviceIdToServiceResponse))
                .totalPrice(reservationCalculation.getTotalPrice())
                .build();
    }

    private Map<String, ServiceResponse> collectServiceIdToServiceResponse(List<ServiceDto> hairdresserServices) {
        var serviceCategories = getServiceCategories(hairdresserServices);
        return hairdresserServices.stream()
                .map(service -> ServiceResponse.of(service, getServiceCategoryName(serviceCategories, service.getId())))
                .collect(toMap(ServiceResponse::getId, identity()));
    }

    private String getServiceCategoryName(List<ServiceCategoryDto> serviceCategories, String serviceId) {
        return serviceCategories.stream()
                .filter(serviceCategory -> serviceCategory.getItemIds().contains(serviceId))
                .findFirst()
                .map(ServiceCategoryDto::getName)
                .orElse("");

    }

    private List<ServiceCategoryDto> getServiceCategories(List<ServiceDto> hairdresserServices) {
        var hairdresserServiceIds = collectHairdresserServiceIds(hairdresserServices);
        var findQuery = ServiceCategoryFindQuery.withServiceIds(hairdresserServiceIds);
        var pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        return serviceCategoryFacade.findAll(findQuery, pageRequest).getContent();
    }

    private Set<String> collectHairdresserServiceIds(List<ServiceDto> hairdresserServices) {
        return hairdresserServices.stream()
                .map(ServiceDto::getId)
                .collect(toSet());
    }

    private List<ServiceResponse> collectSelectedServiceResponses(List<ServiceDto> selectedServices, Map<String, ServiceResponse> serviceIdToServiceResponse) {
        if (isNullOrEmpty(selectedServices)) {
            return null;
        }
        return selectedServices.stream()
                .map(ServiceDto::getId)
                .map(serviceIdToServiceResponse::get)
                .filter(Objects::nonNull)
                .collect(toList());

    }

}
