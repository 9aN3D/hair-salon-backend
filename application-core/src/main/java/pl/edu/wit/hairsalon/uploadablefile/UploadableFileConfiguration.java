package pl.edu.wit.hairsalon.uploadablefile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UploadableFileConfiguration {

    @Bean
    UploadableFileFacade uploadableFileFacade(UploadableFilePort uploadableFilePort,
                                              FileStoragePort fileStoragePort) {
        var storer = new UploadableFileStorer(uploadableFilePort);
        return new AppUploadableFileFacade(uploadableFilePort, fileStoragePort, storer);
    }

}
