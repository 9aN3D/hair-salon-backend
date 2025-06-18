package pl.edu.wit.hairsalon.fileStorage;

import java.util.Objects;
import java.util.StringJoiner;

class FileStorageProperties {

    private String url;

    FileStorageProperties() {
        
    }
    
    FileStorageProperties(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FileStorageProperties that)) return false;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FileStorageProperties.class.getSimpleName() + "[", "]")
                .add("url='" + url + "'")
                .toString();
    }

}
