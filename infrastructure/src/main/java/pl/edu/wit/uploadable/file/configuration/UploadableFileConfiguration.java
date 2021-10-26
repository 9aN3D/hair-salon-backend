package pl.edu.wit.uploadable.file.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pl.edu.wit.uploadable.file.port.primary.UploadableFileService;
import pl.edu.wit.uploadable.file.service.AppUploadableFileService;

@Configuration
@EnableMongoRepositories(basePackages = "pl.edu.wit.uploadable.file.adapter")
public class UploadableFileConfiguration {

    @ConditionalOnMissingBean(UploadableFileService.class)
    @Import(AppUploadableFileService.class)
    @Configuration
    static class UploadableFileServiceConfiguration {

    }

}
