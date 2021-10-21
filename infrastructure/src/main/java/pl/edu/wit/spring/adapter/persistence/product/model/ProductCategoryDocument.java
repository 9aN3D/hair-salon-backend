package pl.edu.wit.spring.adapter.persistence.product.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@QueryEntity
@Document(value = "ProductCategory")
@EqualsAndHashCode(of = {"id"})
public class ProductCategoryDocument {

    @Id
    private String id;

    @Indexed
    private String name;

}
