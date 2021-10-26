package pl.edu.wit.product.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFindQuery {

    private String productId;

    private String productCategoryId;

    private String name;

    private Set<String> productCategoryIds;

    private Set<String> productIds;

    public static ProductFindQuery ofProductId(String productId) {
        return ProductFindQuery.builder()
                .productId(productId)
                .build();
    }

    public static ProductFindQuery ofProductCategoryId(String productCategoryId) {
        return ProductFindQuery.builder()
                .productCategoryId(productCategoryId)
                .build();
    }

    public static ProductFindQuery ofProductName(String name) {
        return ProductFindQuery.builder()
                .name(name)
                .build();
    }

    public static ProductFindQuery ofProductCategoryIds(Set<String> productCategoryIds) {
        return ProductFindQuery.builder()
                .productCategoryIds(productCategoryIds)
                .build();
    }

}
