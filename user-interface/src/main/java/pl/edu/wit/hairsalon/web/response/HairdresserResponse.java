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

public record HairdresserResponse(
        @NotBlank String id,
        @NotBlank String name,
        @NotBlank String surname,
        @NotBlank String fullName,
        String profilePictureUrl,
        @NotNull Set<String> serviceIds,
        List<ServiceResponse> services
) {

    public HairdresserResponse {
        serviceIds = serviceIds != null ? Set.copyOf(serviceIds) : Collections.emptySet();
        services = services != null ? List.copyOf(services) : Collections.emptyList();
    }

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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String id;
        private String name;
        private String surname;
        private String fullName;
        private String profilePictureUrl;
        private Set<String> serviceIds;
        private List<ServiceResponse> services;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder profilePictureUrl(String profilePictureUrl) {
            this.profilePictureUrl = profilePictureUrl;
            return this;
        }

        public Builder serviceIds(Set<String> serviceIds) {
            this.serviceIds = serviceIds != null ? new HashSet<>(serviceIds) : Collections.emptySet();
            return this;
        }

        public Builder services(List<ServiceResponse> services) {
            this.services = services != null ? new ArrayList<>(services) : Collections.emptyList();
            return this;
        }

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
