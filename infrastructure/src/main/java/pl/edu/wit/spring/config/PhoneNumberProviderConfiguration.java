package pl.edu.wit.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.application.port.secondary.PhoneNumberProvider;
import pl.edu.wit.spring.adapter.phone.number.provider.GooglePhoneNumberProvider;

@Configuration
public class PhoneNumberProviderConfiguration {

    @Bean
    PhoneNumberProvider phoneNumberProvider() {
        return new GooglePhoneNumberProvider();
    }

}
