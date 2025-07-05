package pl.edu.wit.hairsalon.hairdresser;

import org.springframework.data.domain.PageRequest;
import pl.edu.wit.hairsalon.scheduledEvent.ScheduledEventFacade;
import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventDto;
import pl.edu.wit.hairsalon.scheduledEvent.query.ScheduledEventFindQuery;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;
import pl.edu.wit.hairsalon.setting.SettingFacade;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.TimeRange;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.Objects.isNull;
import static pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery.ofHairdresserId;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.RESERVATION_GAP_MINUTES;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.RESERVATION_INTERVAL_MINUTES;

class HairdresserAvailabilityFetcher {

    private final HairdresserPort hairdresserPort;
    private final HairdresserDayOverridePort hairdresserDayOverridePort;
    private final SettingFacade settingFacade;
    private final ScheduledEventFacade scheduledEventFacade;
    private final ServiceFacade serviceFacade;

    HairdresserAvailabilityFetcher(HairdresserPort hairdresserPort,
                                   HairdresserDayOverridePort hairdresserDayOverridePort,
                                   SettingFacade settingFacade,
                                   ScheduledEventFacade scheduledEventFacade,
                                   ServiceFacade serviceFacade) {
        this.hairdresserPort = hairdresserPort;
        this.hairdresserDayOverridePort = hairdresserDayOverridePort;
        this.settingFacade = settingFacade;
        this.scheduledEventFacade = scheduledEventFacade;
        this.serviceFacade = serviceFacade;
    }

    public List<Duration> getAvailableTimeDurations(String hairdresserId, LocalDate date) {
        return prepareHairdresserSchedule(hairdresserId, date, Duration.ZERO)
                .map(HairdresserSchedule::availableTimeDurations)
                .orElseGet(List::of);
    }
    
    public List<LocalTime> getAvailableStartTimes(String hairdresserId, LocalDate date, Duration serviceDuration) {
        return prepareHairdresserSchedule(hairdresserId, date, serviceDuration)
                .map(HairdresserSchedule::availableSlots)
                .orElseGet(List::of);
    }
    
    private Optional<HairdresserSchedule> prepareHairdresserSchedule(String hairdresserId, LocalDate date, Duration serviceDuration) {
        var hairdresser = new Hairdresser(hairdresserPort.findOneOrThrow(ofHairdresserId(hairdresserId)));
        var maybeHairdresserWorkingHours = new HairdresserAvailability(hairdresser, this::findHairdresserDayOverride).workingHoursFor(date);
        return maybeHairdresserWorkingHours.map(
                        timeRange -> HairdresserSchedule.builder()
                                .workHours(timeRange)
                                .existingEvents(findEvents(hairdresserId, date))
                                .minServiceDuration(isNull(serviceDuration) ? findHairdresserMinServiceDuration(hairdresser) : serviceDuration)
                                .gapDuration(getSettingFacadeOne(RESERVATION_GAP_MINUTES).valueToDuration())
                                .interval(getSettingFacadeOne(RESERVATION_INTERVAL_MINUTES).valueToDuration())
                                .build())
                .map(HairdresserSchedule::validate);
    }

    private Duration findHairdresserMinServiceDuration(Hairdresser hairdresser) {
        var findQuery = ServiceFindQuery.withIds(hairdresser.services());
        var pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        return serviceFacade.findAll(findQuery, pageRequest).getContent()
                .stream()
                .min(comparing(ServiceDto::durationInMinutes))
                .map(ServiceDto::getDurationInMinutes)
                .orElse(Duration.ZERO);
    }

    private List<TimeRange> findEvents(String hairdresserId, LocalDate date) {
        return scheduledEventFacade.findAll(
                        ScheduledEventFindQuery.builder()
                                .hairdresserId(hairdresserId)
                                .startDateTime(LocalDateTime.of(date, LocalTime.MIN))
                                .endDateTime(LocalDateTime.of(date, LocalTime.MAX))
                                .build())
                .stream()
                .map(ScheduledEventDto::times)
                .map(DateRangeDto::toTimeRangeDto)
                .map(TimeRange::new)
                .sorted(comparing(TimeRange::start))
                .toList();
    }

    private SettingDto getSettingFacadeOne(SettingIdDto id) {
        return settingFacade.findOne(id);
    }

    private Optional<HairdresserDayOverride> findHairdresserDayOverride(HairdresserDayOverrideId overrideId) {
        return hairdresserDayOverridePort.findOne(overrideId.toDto())
                .map(HairdresserDayOverride::of);
    }

}
