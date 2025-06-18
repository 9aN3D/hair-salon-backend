package pl.edu.wit.hairsalon.socialIntegration;

import pl.edu.wit.hairsalon.socialIntegration.command.LinkAddingCalendarEventGenerateCommand;

import static java.util.Objects.requireNonNull;

class AppSocialIntegrationFacade implements SocialIntegrationFacade {

    private final LinkAddingCalendarEventGenerator generator;

    AppSocialIntegrationFacade(LinkAddingCalendarEventGenerator generator) {
        this.generator = generator;
    }

    @Override
    public String generateLinkAddingCalendarEvent(LinkAddingCalendarEventGenerateCommand command) {
        requireNonNull(command, "Link adding calendar event generate command must not be null");
        return generator.generate(command);
    }
}
