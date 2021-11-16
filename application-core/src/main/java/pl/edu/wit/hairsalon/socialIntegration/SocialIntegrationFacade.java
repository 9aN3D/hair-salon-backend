package pl.edu.wit.hairsalon.socialIntegration;

import pl.edu.wit.hairsalon.socialIntegration.command.LinkAddingCalendarEventGenerateCommand;

public interface SocialIntegrationFacade {

    String generateLinkAddingCalendarEvent(LinkAddingCalendarEventGenerateCommand command);

}
