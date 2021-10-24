package pl.edu.wit.spring.config.init;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import pl.edu.wit.spring.adapter.persistence.product.MongoProductCategoryRepository;
import pl.edu.wit.spring.adapter.persistence.product.MongoProductRepository;
import pl.edu.wit.spring.adapter.persistence.product.model.ProductCategoryDocument;
import pl.edu.wit.spring.adapter.persistence.product.model.ProductDocument;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@RequiredArgsConstructor
public class ProductInitializer {

    private final MongoProductRepository productRepository;
    private final MongoProductCategoryRepository productCategoryRepository;

    public void createIfNecessary() {
        var productCategoryDocuments = productCategoryRepository.findAll(PageRequest.of(0, 5));
        if (productCategoryDocuments.isEmpty()) {
            var productCategories = createProductCategories();
            createProducts(productCategories);
        }
    }

    private void createProducts(Map<String, ProductCategoryDocument> nameToProductCategory) {
        productRepository.saveAll(prepareProductDocuments(nameToProductCategory));
    }

    private Map<String, ProductCategoryDocument> createProductCategories() {
        return productCategoryRepository.saveAll(prepareProductCategoryDocuments())
                .stream()
                .collect(toMap(ProductCategoryDocument::getName, identity()));
    }

    private List<ProductCategoryDocument> prepareProductCategoryDocuments() {
        return List.of(
                new ProductCategoryDocument("Strzyżenie"),
                new ProductCategoryDocument("Pielęgnacja"),
                new ProductCategoryDocument("Inne")
        );
    }

    private List<ProductDocument> prepareProductDocuments(Map<String, ProductCategoryDocument> nameToProductCategory) {
        return List.of(
                new ProductDocument("Strzyżenie męskie", new BigDecimal("60.00"), 30L, nameToProductCategory.get("Strzyżenie")),
                new ProductDocument("Strzyżenie studenckie", new BigDecimal("50.00"), 30L, nameToProductCategory.get("Strzyżenie")),
                new ProductDocument("Strzyżenie dzieci do 12 lat", new BigDecimal("50.00"), 30L, nameToProductCategory.get("Strzyżenie")),
                new ProductDocument("Strzyżenie brody", new BigDecimal("50.00"), 25L, nameToProductCategory.get("Strzyżenie")),
                new ProductDocument("Strzyżenie maszynką", new BigDecimal("25.00"), 35L, nameToProductCategory.get("Strzyżenie")),
                new ProductDocument("Strzyżenie włosów i brody", new BigDecimal("40.00"), 40L, nameToProductCategory.get("Strzyżenie")),
                new ProductDocument("Tuszowanie siwizny ze strzyżeniem cover", new BigDecimal("100.00"), 20L, nameToProductCategory.get("Pielęgnacja")),
                new ProductDocument("Tuszowanie siwizny cover bez strzyżeniem", new BigDecimal("45.00"), 25L, nameToProductCategory.get("Pielęgnacja")),
                new ProductDocument("Woskowanie uszu i nosa", new BigDecimal("30.00"), 35L, nameToProductCategory.get("Pielęgnacja")),
                new ProductDocument("Golenie twarzy brzytwą", new BigDecimal("20.00"), 20L, nameToProductCategory.get("Inne"))
        );
    }

}
