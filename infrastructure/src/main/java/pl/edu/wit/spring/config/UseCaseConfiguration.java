package pl.edu.wit.spring.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.edu.wit.application.domain.usecase.hairdresser.DefaultHairdresserUploadPhotoUseCase;
import pl.edu.wit.application.domain.usecase.hairdresser.HairdresserUploadPhotoUseCase;
import pl.edu.wit.application.domain.usecase.product.DefaultProductCreateUseCase;
import pl.edu.wit.application.domain.usecase.product.ProductCreateUseCase;

@Configuration
public class UseCaseConfiguration {

    @ConditionalOnMissingBean(ProductCreateUseCase.class)
    @Import(DefaultProductCreateUseCase.class)
    @Configuration
    static class ProductCreateUseCaseConfiguration {

    }

    @ConditionalOnMissingBean(HairdresserUploadPhotoUseCase.class)
    @Import(DefaultHairdresserUploadPhotoUseCase.class)
    @Configuration
    static class HairdresserUploadPhotoUseCaseConfiguration {

    }

}
