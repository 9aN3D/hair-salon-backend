package pl.edu.wit.hairsalon.hairdresser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserUpdateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;

class LoggableHairdresserFacade implements HairdresserFacade {

    private final Logger log;
    private final HairdresserFacade delegate;

    LoggableHairdresserFacade(HairdresserFacade delegate) {
        this.log = LoggerFactory.getLogger(LoggableHairdresserFacade.class);
        this.delegate = delegate;
    }

    @Override
    public String create(HairdresserCreateCommand command) {
        log.trace("Creating hairdresser {command: {}}", command);
        var result = delegate.create(command);
        log.info("Created hairdresser {id: {}}", result);
        return result;
    }

    @Override
    public void update(String hairdresserId, HairdresserUpdateCommand command) {
        log.trace("Updating hairdresser {hairdresserId: {}, command: {}}", hairdresserId, command);
        delegate.update(hairdresserId, command);
        log.info("Updated hairdresser {hairdresserId: {}}", hairdresserId);
    }

    @Override
    public void uploadPhoto(String hairdresserId, FileUploadCommand command) {
        log.trace("Uploading photo for hairdresser {hairdresserId: {}, command: {}}", hairdresserId, command);
        delegate.uploadPhoto(hairdresserId, command);
        log.info("Uploaded photo for hairdresser {hairdresserId: {}}", hairdresserId);
    }

    @Override
    public HairdresserDto findOne(String hairdresserId) {
        log.trace("Getting hairdresser {hairdresserId: {}}", hairdresserId);
        var dto = delegate.findOne(hairdresserId);
        log.info("Got hairdresser {dto: {}}", dto);
        return dto;
    }

    @Override
    public Page<HairdresserDto> findAll(HairdresserFindQuery findQuery, Pageable pageable) {
        log.trace("Searching hairdressers {findQuery: {}, pageable: {}}", findQuery, pageable);
        var page = delegate.findAll(findQuery, pageable);
        log.info("Searched hairdressers {numberOfElements: {}}", page.getNumberOfElements());
        return page;
    }

}
