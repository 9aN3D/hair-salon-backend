package pl.edu.wit.hairsalon.socialIntegration;

import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.socialIntegration.SocialProvider.GOOGLE;

record GoogleAddingCalendarEvent(
        SocialProvider socialProvider,
        DateRange times,
        String eventName,
        String location
) implements SelfValidator<GoogleAddingCalendarEvent> {

    GoogleAddingCalendarEvent(DateRange times, String eventName, String location) {
        this(GOOGLE, times, eventName, location);
    }

    @Override
    public GoogleAddingCalendarEvent validate() {
        requireNonNull(times, "Google adding calendar event times must not be null");
        validate(times, new NotBlankString(eventName), new NotBlankString(location));
        return this;
    }

    String link() {
        var formatPattern = "yyyyMMdd'T'HHmmss";
        var baseUrl = "http://www.google.com/calendar/event?action=TEMPLATE";
        var dateRange = times.formatStart(formatPattern) + "%2F" + times.formatEnd(formatPattern);

        var joiner = new StringJoiner("&", baseUrl + "&", "");
        joiner.add("dates=" + dateRange);
        joiner.add("text=" + eventName);
        joiner.add("location=" + location);

        return joiner.toString();
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private SocialProvider socialProvider;
        private DateRange times;
        private String eventName;
        private String location;

        Builder socialProvider(SocialProvider socialProvider) {
            this.socialProvider = socialProvider;
            return this;
        }

        Builder times(DateRange times) {
            this.times = times;
            return this;
        }

        Builder eventName(String eventName) {
            this.eventName = eventName;
            return this;
        }

        Builder location(String location) {
            this.location = location;
            return this;
        }

        GoogleAddingCalendarEvent build() {
            return new GoogleAddingCalendarEvent(socialProvider, times, eventName, location);
        }

    }

}
