package pl.edu.wit.hairsalon.socialIntegration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wit.hairsalon.socialIntegration.command.LinkAddingCalendarEventGenerateCommand;

class LoggableSocialIntegrationFacade implements SocialIntegrationFacade {

    private static final Logger log = LoggerFactory.getLogger(LoggableSocialIntegrationFacade.class);
    private final SocialIntegrationFacade delegate;

    LoggableSocialIntegrationFacade(SocialIntegrationFacade delegate) {
        this.delegate = delegate;
    }

    @Override
    public String generateLinkAddingCalendarEvent(LinkAddingCalendarEventGenerateCommand command) {
        log.trace("Generating link adding calendar event {command: {}}", command);
        var result = delegate.generateLinkAddingCalendarEvent(command);
        log.info("Generated link adding calendar event {result: {}}", result);
        return result;
    }
}
