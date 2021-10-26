package pl.edu.wit.phone.number.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.common.port.secondary.PhoneNumberProvider;
import pl.edu.wit.phone.number.adapter.GooglePhoneNumberProvider;

@Configuration
public class PhoneNumberProviderConfiguration {

    @Bean
    PhoneNumberProvider phoneNumberProvider() {
        return new GooglePhoneNumberProvider();
    }

}
