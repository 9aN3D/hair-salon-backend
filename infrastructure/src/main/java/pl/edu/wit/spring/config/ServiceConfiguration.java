package pl.edu.wit.spring.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.edu.wit.application.port.primary.AccessTokenService;
import pl.edu.wit.application.port.primary.AuthDetailsService;
import pl.edu.wit.application.port.primary.HairdresserService;
import pl.edu.wit.application.port.primary.MemberRegistrationService;
import pl.edu.wit.application.port.primary.MemberService;
import pl.edu.wit.application.port.primary.ProductCategoryService;
import pl.edu.wit.application.port.primary.ProductService;
import pl.edu.wit.application.port.primary.UploadableFileService;
import pl.edu.wit.application.port.primary.UserService;
import pl.edu.wit.application.service.AppAccessTokenService;
import pl.edu.wit.application.service.AppAuthDetailsService;
import pl.edu.wit.application.service.AppHairdresserService;
import pl.edu.wit.application.service.AppMemberRegistrationService;
import pl.edu.wit.application.service.AppMemberService;
import pl.edu.wit.application.service.AppProductCategoryService;
import pl.edu.wit.application.service.AppProductService;
import pl.edu.wit.application.service.AppUploadableFileService;
import pl.edu.wit.application.service.AppUserService;
import pl.edu.wit.spring.adapter.file.storage.provider.FileStorageProperties;

@Configuration
@EnableConfigurationProperties(FileStorageProperties.class)
public class ServiceConfiguration {

    @ConditionalOnMissingBean(AccessTokenService.class)
    @Import(AppAccessTokenService.class)
    @Configuration
    static class AccessTokenServiceConfiguration {

    }

    @ConditionalOnMissingBean(AuthDetailsService.class)
    @Import(AppAuthDetailsService.class)
    @Configuration
    static class AuthDetailsServiceConfiguration {

    }

    @ConditionalOnMissingBean(MemberRegistrationService.class)
    @Import(AppMemberRegistrationService.class)
    @Configuration
    static class MemberRegistrationServiceConfiguration {

    }

    @ConditionalOnMissingBean(MemberService.class)
    @Import(AppMemberService.class)
    @Configuration
    static class MemberServiceConfiguration {

    }

    @ConditionalOnMissingBean(UserService.class)
    @Import(AppUserService.class)
    @Configuration
    static class UserServiceConfiguration {

    }

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

    @ConditionalOnMissingBean(UploadableFileService.class)
    @Import(AppUploadableFileService.class)
    @Configuration
    static class UploadableFileServiceConfiguration {

    }

    @ConditionalOnMissingBean(HairdresserService.class)
    @Import(AppHairdresserService.class)
    @Configuration
    static class HairdresserServiceConfiguration {

    }

}
