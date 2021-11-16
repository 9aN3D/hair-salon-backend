package pl.edu.wit.hairsalon.service.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;
import static pl.edu.wit.hairsalon.sharedKernel.CollectionHelper.nonNullOrEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceFindQuery {

    private String id;

    private String name;

    private Set<String> ids;

    public static ServiceFindQuery withId(String serviceId) {
        return ServiceFindQuery.builder()
                .id(serviceId)
                .build();
    }

    public static ServiceFindQuery withName(String name) {
        return ServiceFindQuery.builder()
                .name(name)
                .build();
    }

    public static ServiceFindQuery withIds(Set<String> serviceIds) {
        return ServiceFindQuery.builder()
                .ids(serviceIds)
                .build();
    }

    public void ifIdPresent(Consumer<String> action) {
        if (nonNull(id)) {
            action.accept(id);
        }
    }

    public void ifNamePresent(Consumer<String> action) {
        if (nonNull(name) && !name.isBlank()) {
            action.accept(name);
        }
    }

    public void ifIdsPresent(Consumer<Set<String>> action) {
        if (nonNullOrEmpty(ids)) {
            action.accept(ids);
        }
    }

}
