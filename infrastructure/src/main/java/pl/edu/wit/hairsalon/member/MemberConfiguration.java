package pl.edu.wit.hairsalon.member;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.authDetails.AuthDetailsFacade;

@Configuration(proxyBeanMethods = false)
class MemberConfiguration {

    @Bean
    MemberFacade memberFacade(MemberPort memberPort,
                              PhoneNumberPort phoneNumberPort,
                              AuthDetailsFacade authDetailsFacade) {
        var registration = new MemberRegistration(memberPort, phoneNumberPort, authDetailsFacade);
        var updater = new MemberUpdater(memberPort, phoneNumberPort, authDetailsFacade);
        return new LoggableMemberFacade(
                new AppMemberFacade(memberPort, registration, updater)
        );
    }

}
