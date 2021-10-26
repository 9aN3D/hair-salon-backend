package pl.edu.wit.file.storage.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.file.storage.adapter.FileStorageProperties;

@Configuration
@EnableConfigurationProperties(FileStorageProperties.class)
public class FileStorageConfiguration {

}
