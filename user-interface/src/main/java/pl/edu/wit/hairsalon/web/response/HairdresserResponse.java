package pl.edu.wit.hairsalon.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.reservation.dto.ReservationHairdresserDto;
import pl.edu.wit.hairsalon.uploadableFile.dto.UploadableFileDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class HairdresserResponse {

    @NotBlank
    String id;

    @NotBlank
    String name;

    @NotBlank
    String surname;

    @NotBlank
    String fullName;

    String profilePictureUrl;

    @NotNull
    Set<String> serviceIds;

    List<ServiceResponse> services;

    public static HairdresserResponse of(HairdresserDto dto, Function<String, UploadableFileDto> findUploadableFileFunction) {
        return HairdresserResponse.builder()
                .id(dto.getId())
                .name(dto.getFullName().getName())
                .surname(dto.getFullName().getSurname())
                .fullName(dto.getFullName().toString())
                .profilePictureUrl(findUploadableFileFunction.apply(dto.getPhotoId()).getDownloadUrl())
                .serviceIds(dto.getServiceIds())
                .build();
    }

    public static HairdresserResponse of(ReservationHairdresserDto dto, Collection<ServiceResponse> services, Function<String, UploadableFileDto> findUploadableFileFunction) {
        return HairdresserResponse.builder()
                .id(dto.getId())
                .name(dto.getFullName().getName())
                .surname(dto.getFullName().getSurname())
                .fullName(dto.getFullName().toString())
                .profilePictureUrl(findUploadableFileFunction.apply(dto.getPhotoId()).getDownloadUrl())
                .serviceIds(services.stream()
                        .map(ServiceResponse::getId)
                        .collect(toSet()))
                .services(services.stream()
                        .sorted(comparing(ServiceResponse::getCategoryName))
                        .collect(toList()))
                .build();
    }

}
