package pl.edu.wit.hairsalon.uploadableFile.dto;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public record UploadableFileDto(
        String id,
        String name,
        FileTypeDto type,
        String contentType,
        Long length,
        LocalDateTime uploadDate,
        InputStream content,
        String downloadUrl
) {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UploadableFileDto that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UploadableFileDto.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("name='" + name + "'")
                .add("type=" + type)
                .add("contentType='" + contentType + "'")
                .add("length=" + length)
                .add("uploadDate=" + uploadDate)
                .add("downloadUrl='" + downloadUrl + "'")
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String name;
        private FileTypeDto type;
        private String contentType;
        private Long length;
        private LocalDateTime uploadDate;
        private InputStream content;
        private String downloadUrl;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(FileTypeDto type) {
            this.type = type;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder length(Long length) {
            this.length = length;
            return this;
        }

        public Builder uploadDate(LocalDateTime uploadDate) {
            this.uploadDate = uploadDate;
            return this;
        }

        public Builder content(InputStream content) {
            this.content = content;
            return this;
        }

        public Builder downloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
            return this;
        }

        public UploadableFileDto build() {
            return new UploadableFileDto(id, name, type, contentType, length, uploadDate, content, downloadUrl);
        }

    }

}
