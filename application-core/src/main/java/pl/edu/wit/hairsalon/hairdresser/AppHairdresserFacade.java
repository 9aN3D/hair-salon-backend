package pl.edu.wit.hairsalon.hairdresser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserUpdateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.hairsalon.uploadablefile.command.FileUploadCommand;

import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery.ofHairdresserId;

@Slf4j
@RequiredArgsConstructor
class AppHairdresserFacade implements HairdresserFacade {

    private final HairdresserPort hairdresserPort;
    private final HairdresserCreator creator;
    private final HairdresserUpdater updater;
    private final HairdresserPhotoUploader photoUploader;

    @Override
    public String create(HairdresserCreateCommand command) {
        log.trace("Creating hairdresser {command: {}}", command);
        requireNonNull(command, "Hairdresser create command must not be null");
        var savedHairdresserDto = creator.create(command);
        log.info("Created hairdresser {savedHairdresser: {}}", savedHairdresserDto);
        return savedHairdresserDto.getId();
    }

    @Override
    public void update(String hairdresserId, HairdresserUpdateCommand command) {
        log.trace("Updating hairdresser {hairdresserId: {}, command: {}}", hairdresserId, command);
        requireNonNull(hairdresserId, "Hairdresser id must not be null");
        requireNonNull(command, "Hairdresser update command must not be null");
        var updatedHairdresserDto = updater.update(hairdresserId, command);
        log.info("Updated hairdresser {uploadedHairdresserDto: {}}", updatedHairdresserDto);
    }

    @Override
    public void uploadPhoto(String hairdresserId, FileUploadCommand command) {
        log.trace("Uploading photo for hairdresser {hairdresserId: {}, command: {}}", hairdresserId, command);
        requireNonNull(hairdresserId, "Hairdresser id must not be null");
        requireNonNull(command, "File upload command must not be null");
        var uploadedHairdresserDto = photoUploader.upload(hairdresserId, command);
        log.info("Uploaded photo for hairdresser {uploadedHairdresserDto: {}}", uploadedHairdresserDto);
    }

    @Override
    public HairdresserDto findOne(String hairdresserId) {
        log.trace("Getting hairdresser {hairdresserId: {}}", hairdresserId);
        requireNonNull(hairdresserId, "Hairdresser id must not be null");
        var hairdresserDto = hairdresserPort.findOneOrThrow(ofHairdresserId(hairdresserId));
        log.info("Got hairdresser {hairdresserDto: {}}", hairdresserDto);
        return hairdresserDto;
    }

    @Override
    public Page<HairdresserDto> findAll(HairdresserFindQuery findQuery, Pageable pageable) {
        log.trace("Searching hairdressers {findQuery: {}, pageable: {}}", findQuery, pageable);
        requireNonNull(findQuery, "Hairdresser find query must not be null");
        requireNonNull(pageable, "Pageable must not be null");
        var page = hairdresserPort.findAll(findQuery, pageable);
        log.info("Searched hairdressers {numberOfElements: {}}", page.getNumberOfElements());
        return page;
    }

}
