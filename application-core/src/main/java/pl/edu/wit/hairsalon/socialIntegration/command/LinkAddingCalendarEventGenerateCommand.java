package pl.edu.wit.hairsalon.socialIntegration.command;

import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;
import pl.edu.wit.hairsalon.socialIntegration.dto.SocialProviderDto;

public record LinkAddingCalendarEventGenerateCommand(
        SocialProviderDto socialProvider,
        DateRangeDto times,
        String eventName,
        String location
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private SocialProviderDto socialProvider;
        private DateRangeDto times;
        private String eventName;
        private String location;

        public Builder socialProvider(SocialProviderDto socialProvider) {
            this.socialProvider = socialProvider;
            return this;
        }

        public Builder times(DateRangeDto times) {
            this.times = times;
            return this;
        }

        public Builder eventName(String eventName) {
            this.eventName = eventName;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public LinkAddingCalendarEventGenerateCommand build() {
            return new LinkAddingCalendarEventGenerateCommand(socialProvider, times, eventName, location);
        }
    }
}

