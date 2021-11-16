package pl.edu.wit.hairsalon.socialIntegration;

import pl.edu.wit.hairsalon.sharedKernel.domain.DateRange;
import pl.edu.wit.hairsalon.socialIntegration.command.LinkAddingCalendarEventGenerateCommand;

class LinkAddingCalendarEventGenerator {

    String generate(LinkAddingCalendarEventGenerateCommand command) {
        return createNewGoogleAddingCalendarEvent(command)
                .validate()
                .link();
    }

    private GoogleAddingCalendarEvent createNewGoogleAddingCalendarEvent(LinkAddingCalendarEventGenerateCommand command) {
        return GoogleAddingCalendarEvent.builder()
                .times(new DateRange(command.getTimes()))
                .eventName(command.getEventName())
                .location(command.getLocation())
                .build();
    }

}
