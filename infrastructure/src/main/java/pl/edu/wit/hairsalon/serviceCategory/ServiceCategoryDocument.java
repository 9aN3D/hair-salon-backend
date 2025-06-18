package pl.edu.wit.hairsalon.serviceCategory;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryStatusDto;

import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

@QueryEntity
@Document(value = "ServiceCategory")
class ServiceCategoryDocument {

    @Id
    private String id;

    @Indexed
    private String name;

    private Integer order;

    private ServiceCategoryStatusDto status;

    private Set<String> serviceIds;

    ServiceCategoryDocument() {
    }

    ServiceCategoryDocument(String id, String name, Integer order,
                            ServiceCategoryStatusDto status, Set<String> serviceIds) {
        this.id = id;
        this.name = name;
        this.order = order;
        this.status = status;
        this.serviceIds = serviceIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public ServiceCategoryStatusDto getStatus() {
        return status;
    }

    public void setStatus(ServiceCategoryStatusDto status) {
        this.status = status;
    }

    public Set<String> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(Set<String> serviceIds) {
        this.serviceIds = serviceIds;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ServiceCategoryDocument that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ServiceCategoryDocument.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("name='" + name + "'")
                .add("order=" + order)
                .add("status=" + status)
                .add("serviceIds=" + serviceIds)
                .toString();
    }

}
