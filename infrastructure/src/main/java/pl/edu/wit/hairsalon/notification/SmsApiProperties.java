package pl.edu.wit.hairsalon.notification;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;

@Data
@ConfigurationProperties(prefix = "pl.edu.wit.smsapi")
class SmsApiProperties {

    @NotBlank
    private String token;

    @NotBlank
    private String proxyUrl;

}
