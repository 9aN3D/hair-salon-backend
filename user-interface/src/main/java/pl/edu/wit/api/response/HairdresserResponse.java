package pl.edu.wit.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.application.dto.HairdresserDto;
import pl.edu.wit.application.dto.UploadableFileDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.function.Function;

import static java.lang.String.format;

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
    Set<String> services;

    public static HairdresserResponse of(HairdresserDto dto, Function<String, UploadableFileDto> findUploadableFileFunction) {
        return HairdresserResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .fullName(format("%s %s", dto.getName(), dto.getSurname()))
                .profilePictureUrl(findUploadableFileFunction.apply(dto.getPhotoId()).getDownloadUrl())
                .services(dto.getServices())
                .build();
    }

}
