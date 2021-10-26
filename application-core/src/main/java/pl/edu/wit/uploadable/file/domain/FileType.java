package pl.edu.wit.uploadable.file.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum FileType {

    IMAGE(Set.of("image/jpeg", "image/png", "image/gif"));

    private final Set<String> contentTypes;

    public static FileType getFileType(String name) {
        return Stream.of(values())
                .filter(fileType -> fileType.getContentTypes().contains(name))
                .findFirst()
                .orElse(null);
    }

}
