package pl.edu.wit.hairsalon.scheduledEvent;

import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventTypeDto;

enum ScheduledEventType {

    RESERVED_SERVICE,
    RESERVED_TIME;

    ScheduledEventTypeDto toDto() {
        return ScheduledEventTypeDto.valueOf(this.name());
    }

}
