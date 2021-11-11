package pl.edu.wit.hairsalon.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.hairsalon.servicecategory.dto.ServiceCategoryDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static pl.edu.wit.hairsalon.sharedkernel.CollectionHelper.nonNullOrEmpty;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ServiceCategoryResponse {

    @NotBlank
    String id;

    @NotBlank
    String name;

    @NotNull
    Integer order;

    @NotNull
    List<ServiceResponse> services = new ArrayList<>();

    public static ServiceCategoryResponse of(ServiceCategoryDto dto) {
        return ServiceCategoryResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .order(dto.getOrder())
                .build();
    }

    public void addServices(Set<ServiceResponse> services) {
        if (nonNullOrEmpty(services)) {
            this.services.addAll(services.stream()
                    .sorted(Comparator.comparing(ServiceResponse::getName))
                    .collect(toList()));
        }
    }

}
