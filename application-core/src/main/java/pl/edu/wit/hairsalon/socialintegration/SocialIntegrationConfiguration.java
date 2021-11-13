package pl.edu.wit.hairsalon.socialintegration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SocialIntegrationConfiguration {

    @Bean
    SocialIntegrationFacade socialIntegrationFacade() {
        var addingCalendarEventGenerator = new LinkAddingCalendarEventGenerator();
        return new AppSocialIntegrationFacade(addingCalendarEventGenerator);
    }

}
