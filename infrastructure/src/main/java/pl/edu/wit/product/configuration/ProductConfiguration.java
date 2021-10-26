package pl.edu.wit.product.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pl.edu.wit.product.domain.usecase.DefaultProductCreateUseCase;
import pl.edu.wit.product.domain.usecase.ProductCreateUseCase;
import pl.edu.wit.product.port.primary.ProductCategoryService;
import pl.edu.wit.product.port.primary.ProductService;
import pl.edu.wit.product.service.AppProductCategoryService;
import pl.edu.wit.product.service.AppProductService;

@Configuration
@EnableMongoRepositories(basePackages = "pl.edu.wit.product.adapter")
public class ProductConfiguration {

    @ConditionalOnMissingBean(ProductCategoryService.class)
    @Import(AppProductCategoryService.class)
    @Configuration
    static class ProductCategoryServiceConfiguration {

    }

    @ConditionalOnMissingBean(ProductService.class)
    @Import(AppProductService.class)
    @Configuration
    static class ProductServiceConfiguration {

    }

    @ConditionalOnMissingBean(ProductCreateUseCase.class)
    @Import(DefaultProductCreateUseCase.class)
    @Configuration
    static class ProductCreateUseCaseConfiguration {

    }

}
