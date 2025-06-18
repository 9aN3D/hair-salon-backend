package pl.edu.wit.hairsalon.hairdresser;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;

import java.util.Objects;
import java.util.Set;

@QueryEntity
@Document(value = "Hairdresser")
class HairdresserDocument {

    private String id;

    private EmbeddableHairdresserFullName fullName;

    private String photoId;

    private Set<String> serviceIds;

    HairdresserDocument() {
    }

    HairdresserDocument(String id, EmbeddableHairdresserFullName fullName, String photoId, Set<String> serviceIds) {
        this.id = id;
        this.fullName = fullName;
        this.photoId = photoId;
        this.serviceIds = serviceIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EmbeddableHairdresserFullName getFullName() {
        return fullName;
    }

    public void setFullName(EmbeddableHairdresserFullName fullName) {
        this.fullName = fullName;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public Set<String> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(Set<String> serviceIds) {
        this.serviceIds = serviceIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HairdresserDocument that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private String id;
        private EmbeddableHairdresserFullName fullName;
        private String photoId;
        private Set<String> serviceIds;

        Builder id(String id) {
            this.id = id;
            return this;
        }

        Builder fullName(EmbeddableHairdresserFullName fullName) {
            this.fullName = fullName;
            return this;
        }

        Builder photoId(String photoId) {
            this.photoId = photoId;
            return this;
        }

        Builder serviceIds(Set<String> serviceIds) {
            this.serviceIds = serviceIds;
            return this;
        }

        HairdresserDocument build() {
            return new HairdresserDocument(id, fullName, photoId, serviceIds);
        }

    }

}

