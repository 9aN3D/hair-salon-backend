package pl.edu.wit.hairsalon.serviceCategory;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.service.ServiceFacade;

import java.util.Set;

import static pl.edu.wit.hairsalon.service.query.ServiceFindQuery.withIds;

@RequiredArgsConstructor
class ServiceCategoryItemIds {

    private final ServiceFacade serviceFacade;

    boolean isExists(Set<String> serviceIds) {
        return getServicesCount(serviceIds) != serviceIds.size();
    }

    private long getServicesCount(Set<String> serviceIds) {
        return serviceFacade.count(withIds(serviceIds));
    }

}
