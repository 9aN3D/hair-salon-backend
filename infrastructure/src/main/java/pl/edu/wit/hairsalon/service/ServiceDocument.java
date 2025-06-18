package pl.edu.wit.hairsalon.service;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.sharedKernel.document.EmbeddableMoney;
import pl.edu.wit.hairsalon.sharedKernel.dto.MoneyDto;

import java.util.Objects;
import java.util.StringJoiner;

@QueryEntity
@Document(value = "Service")
class ServiceDocument {

    @Id
    private String id;

    @Indexed
    private String name;

    private EmbeddableMoney price;

    private Integer durationInMinutes;

    ServiceDocument() {
    }

    ServiceDocument(String id, String name, EmbeddableMoney price, Integer durationInMinutes) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.durationInMinutes = durationInMinutes;
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

    public EmbeddableMoney getPrice() {
        return price;
    }

    public void setPrice(EmbeddableMoney price) {
        this.price = price;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ServiceDocument that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ServiceDocument.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("name='" + name + "'")
                .add("price=" + price)
                .add("durationInMinutes=" + durationInMinutes)
                .toString();
    }

}
