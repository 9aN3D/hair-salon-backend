package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.reservation.dto.ReservationHairdresserDto;
import pl.edu.wit.hairsalon.uploadableFile.dto.UploadableFileDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/**
 * Reprezentuje fryzjera wykonującego usługi w salonie.
 *
 * @param id                 identyfikator fryzjera (wymagany, niepusty)
 * @param name               imię fryzjera
 * @param surname            nazwisko fryzjera
 * @param fullName           pełne imię i nazwisko
 * @param profilePictureUrl URL do zdjęcia profilowego (może być null)
 * @param serviceIds         identyfikatory usług, które fryzjer może wykonać
 * @param services           szczegóły usług fryzjera (opcjonalne, może być pusta lista)
 *
 * @see ServiceResponse
 */
public record HairdresserResponse(
        @NotBlank String id,
        @NotBlank String name,
        @NotBlank String surname,
        @NotBlank String fullName,
        String profilePictureUrl,
        @NotNull Set<String> serviceIds,
        List<ServiceResponse> services
) {

    /**
     * Konstruktor zapewniający niemutowalność kolekcji.
     *
     * @param id                 identyfikator fryzjera
     * @param name               imię
     * @param surname            nazwisko
     * @param fullName           imię i nazwisko
     * @param profilePictureUrl link do zdjęcia
     * @param serviceIds         identyfikatory usług
     * @param services           lista usług
     */
    public HairdresserResponse {
        serviceIds = serviceIds != null ? Set.copyOf(serviceIds) : Collections.emptySet();
        services = services != null ? List.copyOf(services) : Collections.emptyList();
    }

    /**
     * Tworzy odpowiedź na podstawie {@link HairdresserDto} – bez szczegółowych danych usług.
     *
     * @param dto                         DTO fryzjera
     * @param findUploadableFileFunction funkcja pobierająca zdjęcie po ID
     * @return odpowiedź o fryzjerze
     */
    public static HairdresserResponse of(HairdresserDto dto,
                                         Function<String, UploadableFileDto> findUploadableFileFunction) {
        String profileUrl = dto.photoId() != null ? findUploadableFileFunction.apply(dto.photoId()).downloadUrl() : null;
        return builder()
                .id(dto.id())
                .name(dto.fullName().name())
                .surname(dto.fullName().surname())
                .fullName(dto.fullName().toString())
                .profilePictureUrl(profileUrl)
                .serviceIds(dto.serviceIds())
                .build();
    }

    /**
     * Tworzy odpowiedź na podstawie {@link ReservationHairdresserDto} i pełnych danych usług.
     *
     * @param dto                         DTO fryzjera
     * @param services                    lista usług dostępnych dla tego fryzjera
     * @param findUploadableFileFunction funkcja pobierająca zdjęcie po ID
     * @return odpowiedź o fryzjerze z pełnymi informacjami o usługach
     */
    public static HairdresserResponse of(ReservationHairdresserDto dto,
                                         Collection<ServiceResponse> services, Function<String, UploadableFileDto> findUploadableFileFunction) {
        String profileUrl = nonNull(dto.photoId()) ? findUploadableFileFunction.apply(dto.photoId()).downloadUrl() : null;
        Set<String> serviceIds = services.stream()
                .map(ServiceResponse::id)
                .collect(Collectors.toSet());
        List<ServiceResponse> sortedServices = services.stream()
                .sorted(Comparator.comparing(ServiceResponse::categoryName, Comparator.nullsLast(String::compareTo)))
                .toList();
        return builder()
                .id(dto.id())
                .name(dto.fullName().name())
                .surname(dto.fullName().surname())
                .fullName(dto.fullName().toString())
                .profilePictureUrl(profileUrl)
                .serviceIds(serviceIds)
                .services(sortedServices)
                .build();
    }

    /**
     * Tworzy nową instancję buildera dla {@link HairdresserResponse}.
     *
     * @return builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder dla {@link HairdresserResponse}, zapewniający kontrolowaną konstrukcję.
     */
    public static final class Builder {

        private String id;
        private String name;
        private String surname;
        private String fullName;
        private String profilePictureUrl;
        private Set<String> serviceIds;
        private List<ServiceResponse> services;

        /**
         * Ustawia identyfikator fryzjera.
         *
         * @param id identyfikator
         * @return builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Ustawia imię fryzjera.
         *
         * @param name imię
         * @return builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Ustawia nazwisko fryzjera.
         *
         * @param surname nazwisko
         * @return builder
         */
        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        /**
         * Ustawia pełne imię i nazwisko fryzjera.
         *
         * @param fullName pełna forma imienia i nazwiska
         * @return builder
         */
        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        /**
         * Ustawia URL do zdjęcia profilowego fryzjera.
         *
         * @param profilePictureUrl link do pliku (może być null)
         * @return builder
         */
        public Builder profilePictureUrl(String profilePictureUrl) {
            this.profilePictureUrl = profilePictureUrl;
            return this;
        }

        /**
         * Ustawia zbiór identyfikatorów usług, które fryzjer może wykonywać.
         *
         * @param serviceIds zbiór ID usług
         * @return builder
         */
        public Builder serviceIds(Set<String> serviceIds) {
            this.serviceIds = serviceIds != null ? new HashSet<>(serviceIds) : Collections.emptySet();
            return this;
        }

        /**
         * Ustawia szczegółową listę usług dostępnych u fryzjera.
         *
         * @param services lista usług (może być pusta lub null)
         * @return builder
         */
        public Builder services(List<ServiceResponse> services) {
            this.services = services != null ? new ArrayList<>(services) : Collections.emptyList();
            return this;
        }

        /**
         * Tworzy obiekt {@link HairdresserResponse}, upewniając się, że pola wymagane nie są null.
         *
         * @return zbudowany obiekt odpowiedzi
         * @throws NullPointerException jeśli któreś z wymaganych pól nie zostało ustawione
         */
        public HairdresserResponse build() {
            requireNonNull(id, "id must not be null");
            requireNonNull(name, "name must not be null");
            requireNonNull(surname, "surname must not be null");
            requireNonNull(fullName, "fullName must not be null");
            requireNonNull(serviceIds, "serviceIds must not be null");
            return new HairdresserResponse(id, name, surname, fullName, profilePictureUrl, serviceIds, services);
        }

    }

}
