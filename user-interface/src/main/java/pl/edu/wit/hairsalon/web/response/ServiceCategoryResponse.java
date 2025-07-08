package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/**
 * Odpowiedź reprezentująca kategorię usług w salonie.
 *
 * @param id       identyfikator kategorii
 * @param name     nazwa kategorii (np. "Strzyżenie męskie")
 * @param order    kolejność wyświetlania kategorii
 * @param services lista usług w danej kategorii (może być pusta, nigdy null)
 *
 * @see ServiceResponse
 * @see ServiceCategoryDto
 */
public record ServiceCategoryResponse(
        @NotBlank String id,
        @NotBlank String name,
        @NotNull Integer order,
        @NotNull List<ServiceResponse> services
) {

    /**
     * Konstruktor zapewniający niemutowalność listy usług.
     *
     * @param id       identyfikator kategorii
     * @param name     nazwa
     * @param order    kolejność
     * @param services lista usług (jeśli null, zostanie zamieniona na pustą)
     */
    public ServiceCategoryResponse {
        services = nonNull(services) ? new ArrayList<>(services) : new ArrayList<>();
    }


    /**
     * Tworzy obiekt odpowiedzi na podstawie DTO kategorii.
     * Usługi nie są ustawiane — można je dodać osobno metodą {@link #addServices(Set)}.
     *
     * @param dto DTO kategorii usług
     * @return nowa instancja odpowiedzi
     */
    public static ServiceCategoryResponse of(ServiceCategoryDto dto) {
        return builder()
                .id(dto.id())
                .name(dto.name())
                .order(dto.order())
                .build();
    }

    /**
     * Dodaje zestaw usług do tej kategorii (np. po mapowaniu z zewnętrznego źródła).
     * Usługi są sortowane alfabetycznie po nazwie.
     *
     * @param servicesToAdd usługi do dodania (ignoruje null lub pustą kolekcję)
     */
    public void addServices(Set<ServiceResponse> servicesToAdd) {
        if (servicesToAdd != null && !servicesToAdd.isEmpty()) {
            services().addAll(servicesToAdd.stream()
                    .sorted(Comparator.comparing(ServiceResponse::name))
                    .toList());
        }
    }

    /**
     * Tworzy instancję buildera dla {@link ServiceCategoryResponse}.
     *
     * @return builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder dla {@link ServiceCategoryResponse}.
     */
    public static final class Builder {

        private String id;
        private String name;
        private Integer order;
        private List<ServiceResponse> services = new ArrayList<>();

        /** Ustawia identyfikator kategorii.
         * 
         * @param id identyfikator
         * @return builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Ustawia nazwę kategorii.
         * 
         * @param name nazwa kategorii
         * @return builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /** Ustawia kolejność wyświetlania kategorii.
         * 
         * @param order kolejność wyświetlania
         * @return builder
         */
        public Builder order(Integer order) {
            this.order = order;
            return this;
        }

        /** Ustawia listę usług należących do tej kategorii.
         * 
         * @param services lista usług
         * @return builder
         */
        public Builder services(List<ServiceResponse> services) {
            if (services != null) {
                this.services = new ArrayList<>(services);
            }
            return this;
        }

        /**
         * Tworzy instancję {@link ServiceCategoryResponse}, walidując wymagane pola.
         *
         * @return gotowy obiekt odpowiedzi
         * @throws NullPointerException jeśli któreś z wymaganych pól jest null
         */
        public ServiceCategoryResponse build() {
            requireNonNull(id, "id must not be null");
            requireNonNull(name, "name must not be null");
            requireNonNull(order, "order must not be null");
            return new ServiceCategoryResponse(id, name, order, services);
        }

    }

}

