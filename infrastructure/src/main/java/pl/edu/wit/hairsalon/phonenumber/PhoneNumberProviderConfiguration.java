package pl.edu.wit.hairsalon.phonenumber;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.member.PhoneNumberPort;

@Configuration
class PhoneNumberProviderConfiguration {

    @Bean
    PhoneNumberPort phoneNumberProvider() {
        return new GooglePhoneNumberAdapter();
    }

}
