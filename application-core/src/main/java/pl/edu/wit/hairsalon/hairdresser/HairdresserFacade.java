package pl.edu.wit.hairsalon.hairdresser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserUpdateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface HairdresserFacade {

    String create(HairdresserCreateCommand command);

    void update(String hairdresserId, HairdresserUpdateCommand command);

    Page<HairdresserDto> findAll(HairdresserFindQuery findQuery, Pageable pageable);

    void uploadPhoto(String hairdresserId, FileUploadCommand command);

    HairdresserDto findOne(String hairdresserId);

    List<LocalTime> getAvailableStartTimes(String hairdresserId, LocalDate date, Duration serviceDuration);

    List<Duration> getAvailableTimeDurations(String hairdresserId, LocalDate date);

    default List<HairdresserDto> findAll(HairdresserFindQuery findQuery) {
        return findAll(findQuery, PageRequest.of(0, Integer.MAX_VALUE)).getContent();
    }

    default List<LocalTime> getAvailableStartTimes(String hairdresserId, LocalDate date) {
        return getAvailableStartTimes(hairdresserId, date, null);
    }

}
