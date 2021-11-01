package pl.edu.wit.hairsalon.servicecategory.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.hairsalon.servicecategory.dto.ServiceCategoryStatusDto;

import java.util.Set;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;
import static pl.edu.wit.hairsalon.sharedkernel.CollectionHelper.nonNullOrEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCategoryFindQuery {

    private String name;

    private String serviceCategoryId;

    private ServiceCategoryStatusDto status;

    private Set<String> serviceIds;

    public static ServiceCategoryFindQuery withName(String name) {
        return ServiceCategoryFindQuery.builder()
                .name(name)
                .build();
    }

    public static ServiceCategoryFindQuery withCategoryId(String serviceCategoryId) {
        return ServiceCategoryFindQuery.builder()
                .serviceCategoryId(serviceCategoryId)
                .build();
    }

    public static ServiceCategoryFindQuery withServiceId(String serviceId) {
        return ServiceCategoryFindQuery.builder()
                .serviceIds(Set.of(serviceId))
                .build();
    }

    public static ServiceCategoryFindQuery withServiceIds(Set<String> serviceIds) {
        return ServiceCategoryFindQuery.builder()
                .serviceIds(serviceIds)
                .build();
    }

    public void ifNamePresent(Consumer<String> action) {
        if (nonNull(name) && !name.isBlank()) {
            action.accept(name);
        }
    }

    public void ifServiceCategoryIdPresent(Consumer<String> action) {
        if (nonNull(serviceCategoryId) && !name.isBlank()) {
            action.accept(serviceCategoryId);
        }
    }

    public void ifStatusPresent(Consumer<ServiceCategoryStatusDto> action) {
        if (nonNull(status)) {
            action.accept(status);
        }
    }

    public void ifServiceIdsPresent(Consumer<Set<String>> action) {
        if (nonNullOrEmpty(serviceIds)) {
            action.accept(serviceIds);
        }
    }

}
