package pl.edu.wit.spring.adapter.persistence.product.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.application.domain.model.product.ProductType;

import java.math.BigDecimal;

@Data
@Builder
@QueryEntity
@Document(value = "Product")
@EqualsAndHashCode(of = {"id"})
public class ProductDocument {

    @Id
    private String id;

    @Indexed
    private String name;

    private ProductType type;

    private BigDecimal price;

    private Long durationInMinutes;

    @DBRef
    private ProductCategoryDocument category;

}
