package pl.edu.wit.hairsalon.socialIntegration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class SocialIntegrationConfiguration {

    @Bean
    SocialIntegrationFacade socialIntegrationFacade() {
        var addingCalendarEventGenerator = new LinkAddingCalendarEventGenerator();
        return new LoggableSocialIntegrationFacade(
                new AppSocialIntegrationFacade(addingCalendarEventGenerator)
        );
    }

}
