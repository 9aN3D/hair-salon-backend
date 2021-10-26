package pl.edu.wit.product.adapter.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@QueryEntity
@NoArgsConstructor
@Document(value = "ProductCategory")
@EqualsAndHashCode(of = {"id"})
public class ProductCategoryDocument {

    @Id
    private String id;

    @Indexed
    private String name;

    public ProductCategoryDocument(String name) {
        this.name = name;
    }

}
