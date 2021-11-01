package pl.edu.wit.hairsalon.filestorage;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FileStorageAdapter.class)
class FileStorageConfiguration {

}
