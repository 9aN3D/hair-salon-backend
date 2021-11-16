package pl.edu.wit.hairsalon.socialIntegration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.hairsalon.socialIntegration.command.LinkAddingCalendarEventGenerateCommand;

import static java.util.Objects.requireNonNull;

@Slf4j
@RequiredArgsConstructor
class AppSocialIntegrationFacade implements SocialIntegrationFacade {

    private final LinkAddingCalendarEventGenerator generator;

    @Override
    public String generateLinkAddingCalendarEvent(LinkAddingCalendarEventGenerateCommand command) {
        log.trace("Generating link adding calendar event {command: {}}", command);
        requireNonNull(command, "Link adding calendar event generate command must not be null");
        var link = generator.generate(command);
        log.info("Generated link adding calendar event {result: {}}", link);
        return link;
    }

}
