package pl.edu.wit.hairsalon.scheduledevent;

import pl.edu.wit.hairsalon.scheduledevent.dto.ScheduledEventTypeDto;

enum ScheduledEventType {

    RESERVED_SERVICE,
    RESERVED_TIME;

    ScheduledEventTypeDto toDto() {
        return ScheduledEventTypeDto.valueOf(this.name());
    }

}
