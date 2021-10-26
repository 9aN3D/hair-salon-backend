package pl.edu.wit.product.adapter.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.product.domain.ProductType;

import java.math.BigDecimal;

import static pl.edu.wit.product.domain.ProductType.SERVICE;

@Data
@Builder
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
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

    public ProductDocument(String name,
                           BigDecimal price,
                           Long durationInMinutes,
                           ProductCategoryDocument category) {
        this.name = name;
        this.type = SERVICE;
        this.price = price;
        this.durationInMinutes = durationInMinutes;
        this.category = category;
    }

}
