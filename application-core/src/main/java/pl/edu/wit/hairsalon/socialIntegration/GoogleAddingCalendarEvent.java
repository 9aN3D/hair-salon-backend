package pl.edu.wit.hairsalon.socialIntegration;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.socialIntegration.SocialProvider.GOOGLE;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "location")
class GoogleAddingCalendarEvent implements SelfValidator<GoogleAddingCalendarEvent> {

    private final SocialProvider socialProvider = GOOGLE;
    private final DateRange times;
    private final String eventName;
    private final String location;

    @Override
    public GoogleAddingCalendarEvent validate() {
        requireNonNull(times, "Google adding calendar event times must not be null");
        validate(times, new NotBlankString(eventName), new NotBlankString(location));
        return this;
    }

    String link() {
        var formatPattern = "yyyyMMdd'T'HHmmss";
        return new StringBuilder()
                .append("http://www.google.com/calendar/event?action=TEMPLATE&dates=")
                .append(times.formatStart(formatPattern))
                .append("%2F")
                .append(times.formatEnd(formatPattern))
                .append("&text=")
                .append(eventName)
                .append("&location=")
                .append(location)
                .toString();
    }

}
