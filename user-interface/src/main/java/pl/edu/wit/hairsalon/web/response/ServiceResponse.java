package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.MoneyDto;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Odpowiedź reprezentująca pojedynczą usługę dostępną w salonie.
 *
 * @param id                 identyfikator usługi
 * @param name               nazwa usługi (np. "Strzyżenie męskie")
 * @param price              cena usługi jako {@link MoneyDto}
 * @param durationInMinutes  czas trwania usługi w minutach
 * @param categoryName       opcjonalna nazwa kategorii (np. "Strzyżenie")
 * @param categoryOrder      opcjonalna kolejność wyświetlania kategorii
 *
 * @see ServiceDto
 * @see ServiceCategoryDto
 * @see MoneyDto
 */
public record ServiceResponse(
        @NotBlank String id,
        @NotBlank String name,
        @NotNull MoneyDto price,
        @NotNull Long durationInMinutes,
        String categoryName,
        Integer categoryOrder
) {

    /**
     * Porównuje usługi po ich identyfikatorze.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ServiceResponse that)) return false;
        return Objects.equals(id, that.id);
    }

    /**
     * Hashcode oparty wyłącznie na identyfikatorze usługi.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Tworzy odpowiedź na podstawie DTO usługi, bez informacji o kategorii.
     *
     * @param dto DTO usługi
     * @return obiekt odpowiedzi
     */
    public static ServiceResponse of(ServiceDto dto) {
        return builder()
                .id(dto.id())
                .name(dto.name())
                .price(dto.price())
                .durationInMinutes(dto.durationInMinutes())
                .build();
    }

    /**
     * Tworzy odpowiedź na podstawie DTO usługi oraz DTO kategorii.
     *
     * @param dto     DTO usługi
     * @param category DTO kategorii, do której należy usługa
     * @return obiekt odpowiedzi z danymi kategorii
     */
    public static ServiceResponse of(ServiceDto dto, ServiceCategoryDto category) {
        return builder()
                .id(dto.id())
                .name(dto.name())
                .price(dto.price())
                .durationInMinutes(dto.durationInMinutes())
                .categoryName(category.name())
                .categoryOrder(category.order())
                .build();
    }

    /**
     * Tworzy builder dla {@link ServiceResponse}.
     *
     * @return nowa instancja buildera
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder dla {@link ServiceResponse}, umożliwiający bezpieczne tworzenie instancji.
     */
    public static final class Builder {

        private String id;
        private String name;
        private MoneyDto price;
        private Long durationInMinutes;
        private String categoryName;
        private Integer categoryOrder;

        /** Ustawia identyfikator usługi.
         * 
         * @param id identyfikator
         * @return builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Ustawia nazwę usługi.
         * 
         * @param name nazwa usługi
         * @return builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /** Ustawia cenę usługi. 
         * 
         * @param price cena usługi
         * @return builder
         */
        public Builder price(MoneyDto price) {
            this.price = price;
            return this;
        }

        /** Ustawia czas trwania usługi w minutach. 
         * 
         * @param durationInMinutes czas trwania usługi
         * @return builder
         */
        public Builder durationInMinutes(Long durationInMinutes) {
            this.durationInMinutes = durationInMinutes;
            return this;
        }

        /** Ustawia nazwę kategorii (opcjonalnie). 
         * 
         * @param categoryName nazwa kategorii
         * @return builder
         */
        public Builder categoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        /** Ustawia kolejność kategorii (opcjonalnie).
         * 
         * @param categoryOrder kolejność kategorii
         * @return builder
         */
        public Builder categoryOrder(Integer categoryOrder) {
            this.categoryOrder = categoryOrder;
            return this;
        }

        /**
         * Buduje nową instancję {@link ServiceResponse}, weryfikując wymagane pola.
         *
         * @return gotowy obiekt odpowiedzi
         * @throws NullPointerException jeśli któreś z wymaganych pól są null
         */
        public ServiceResponse build() {
            requireNonNull(id, "id must not be null");
            requireNonNull(name, "name must not be null");
            requireNonNull(price, "price must not be null");
            requireNonNull(durationInMinutes, "durationInMinutes must not be null");
            return new ServiceResponse(id, name, price, durationInMinutes, categoryName, categoryOrder);
        }

    }

}
