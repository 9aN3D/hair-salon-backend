package pl.edu.wit.hairsalon.uploadableFile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class UploadableFileConfiguration {

    @Bean
    UploadableFileFacade uploadableFileFacade(UploadableFilePort uploadableFilePort,
                                              FileStoragePort fileStoragePort) {
        var storer = new UploadableFileStorer(uploadableFilePort);
        return new LoggableUploadableFileFacade(
                new AppUploadableFileFacade(uploadableFilePort, fileStoragePort, storer)
        );
    }

}
