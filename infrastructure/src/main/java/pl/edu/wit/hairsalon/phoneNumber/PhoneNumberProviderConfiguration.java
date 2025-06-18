package pl.edu.wit.hairsalon.phoneNumber;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.member.PhoneNumberPort;

@Configuration(proxyBeanMethods = false)
class PhoneNumberProviderConfiguration {

    @Bean
    PhoneNumberPort phoneNumberProvider() {
        return new GooglePhoneNumberAdapter();
    }

}
