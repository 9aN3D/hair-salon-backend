package pl.edu.wit.hairsalon.uploadablefile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.uploadablefile.dto.FileTypeDto;

import java.util.Set;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
enum FileType {

    IMAGE(Set.of("image/jpeg", "image/png", "image/gif"));

    private final Set<String> contentTypes;

    public static FileType getFileType(String name) {
        return Stream.of(values())
                .filter(fileType -> fileType.getContentTypes().contains(name))
                .findFirst()
                .orElse(null);
    }

    public FileTypeDto toDto() {
        return FileTypeDto.valueOf(IMAGE.name());
    }

}
