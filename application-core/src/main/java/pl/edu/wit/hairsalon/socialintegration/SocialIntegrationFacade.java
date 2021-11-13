package pl.edu.wit.hairsalon.socialintegration;

import pl.edu.wit.hairsalon.socialintegration.command.LinkAddingCalendarEventGenerateCommand;

public interface SocialIntegrationFacade {

    String generateLinkAddingCalendarEvent(LinkAddingCalendarEventGenerateCommand command);

}
