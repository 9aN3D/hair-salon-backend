package pl.edu.wit.hairsalon.hairdresser;

import org.springframework.data.domain.Page;
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

import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery.ofHairdresserId;

class AppHairdresserFacade implements HairdresserFacade {

    private final HairdresserPort hairdresserPort;
    private final HairdresserCreator creator;
    private final HairdresserUpdater updater;
    private final HairdresserPhotoUploader photoUploader;
    private final HairdresserAvailabilityFetcher availabilityFetcher;

    AppHairdresserFacade(HairdresserPort hairdresserPort,
                         HairdresserCreator creator,
                         HairdresserUpdater updater,
                         HairdresserPhotoUploader photoUploader,
                         HairdresserAvailabilityFetcher availabilityFetcher) {
        this.hairdresserPort = hairdresserPort;
        this.creator = creator;
        this.updater = updater;
        this.photoUploader = photoUploader;
        this.availabilityFetcher = availabilityFetcher;
    }

    @Override
    public String create(HairdresserCreateCommand command) {
        requireNonNull(command, "Hairdresser create command must not be null");
        var savedHairdresserDto = creator.create(command);
        return savedHairdresserDto.id();
    }

    @Override
    public void update(String hairdresserId, HairdresserUpdateCommand command) {
        requireNonNull(hairdresserId, "Hairdresser id must not be null");
        requireNonNull(command, "Hairdresser update command must not be null");
        updater.update(hairdresserId, command);
    }

    @Override
    public void uploadPhoto(String hairdresserId, FileUploadCommand command) {
        requireNonNull(hairdresserId, "Hairdresser id must not be null");
        requireNonNull(command, "File upload command must not be null");
        photoUploader.upload(hairdresserId, command);
    }

    @Override
    public HairdresserDto findOne(String hairdresserId) {
        requireNonNull(hairdresserId, "Hairdresser id must not be null");
        return hairdresserPort.findOneOrThrow(ofHairdresserId(hairdresserId));
    }

    @Override
    public List<LocalTime> getAvailableStartTimes(String hairdresserId, LocalDate date, Duration serviceDuration) {
        requireNonNull(hairdresserId, "HairdresserId must not be null");
        requireNonNull(date, "Date must not be null");
        return availabilityFetcher.getAvailableStartTimes(hairdresserId, date, serviceDuration);
    }

    @Override
    public List<Duration> getAvailableTimeDurations(String hairdresserId, LocalDate date) {
        requireNonNull(hairdresserId, "HairdresserId must not be null");
        requireNonNull(date, "Date must not be null");
        return availabilityFetcher.getAvailableTimeDurations(hairdresserId, date);
    }

    @Override
    public Page<HairdresserDto> findAll(HairdresserFindQuery findQuery, Pageable pageable) {
        requireNonNull(findQuery, "Hairdresser find query must not be null");
        requireNonNull(pageable, "Pageable must not be null");
        return hairdresserPort.findAll(findQuery, pageable);
    }

}
