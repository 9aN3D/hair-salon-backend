package pl.edu.wit.hairsalon.uploadableFile.command;

import java.io.InputStream;
import java.util.Objects;
import java.util.StringJoiner;

public record FileUploadCommand(
        String originalFilename,
        String contentType,
        Long size,
        InputStream content
) {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FileUploadCommand that)) return false;
        return Objects.equals(originalFilename, that.originalFilename);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(originalFilename);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FileUploadCommand.class.getSimpleName() + "[", "]")
                .add("originalFilename='" + originalFilename + "'")
                .add("contentType='" + contentType + "'")
                .add("size=" + size)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String originalFilename;
        private String contentType;
        private Long size;
        private InputStream content;

        public Builder originalFilename(String originalFilename) {
            this.originalFilename = originalFilename;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder size(Long size) {
            this.size = size;
            return this;
        }

        public Builder content(InputStream content) {
            this.content = content;
            return this;
        }

        public FileUploadCommand build() {
            return new FileUploadCommand(originalFilename, contentType, size, content);
        }

    }

}
