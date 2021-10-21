package pl.edu.wit.application.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryFindQuery {

    private String name;

    private String productCategoryId;

    public static ProductCategoryFindQuery ofProductCategoryName(String name) {
        return ProductCategoryFindQuery.builder()
                .name(name)
                .build();
    }

    public static ProductCategoryFindQuery ofProductCategoryId(String productCategoryId) {
        return ProductCategoryFindQuery.builder()
                .productCategoryId(productCategoryId)
                .build();
    }

}
