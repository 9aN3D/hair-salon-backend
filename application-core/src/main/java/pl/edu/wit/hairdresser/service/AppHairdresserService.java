package pl.edu.wit.hairdresser.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.uploadable.file.command.FileUploadCommand;
import pl.edu.wit.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.hairdresser.command.HairdresserUpdateCommand;
import pl.edu.wit.hairdresser.dto.HairdresserDto;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.hairdresser.exception.HairdresserNotFoundException;
import pl.edu.wit.hairdresser.port.primary.HairdresserService;
import pl.edu.wit.uploadable.file.port.primary.UploadableFileService;
import pl.edu.wit.hairdresser.port.secondary.HairdresserDao;
import pl.edu.wit.common.port.secondary.IdGenerator;
import pl.edu.wit.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.common.query.PageableParamsQuery;
import pl.edu.wit.hairdresser.domain.Hairdresser;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public class AppHairdresserService implements HairdresserService {

    private final IdGenerator idGenerator;
    private final HairdresserDao hairdresserDao;
    private final UploadableFileService uploadableFileService;

    @Override
    public String create(HairdresserCreateCommand command) {
        log.trace("Creating hairdresser {command: {}}", command);
        var hairdresser = buildHairdresser(command);
        var savedHairdresserId = hairdresserDao.save(hairdresser.toDto());
        log.info("Created hairdresser {savedHairdresser: {}}", hairdresser);
        return savedHairdresserId;
    }

    @Override
    public void uploadPhoto(String hairdresserId, FileUploadCommand command) {
        log.trace("Uploading photo for hairdresser {hairdresserId: {}, command: {}}", hairdresserId, command);
        var hairdresserDto = getOneFromDaoOrThrow(hairdresserId);
        var hairdresser = new Hairdresser(hairdresserDto);
        var storeId = uploadableFileService.store(command);
        if (hairdresser.hasPhotoId()) {
            uploadableFileService.delete(hairdresser.phoneId());
        }
        var uploadedHairdresserDto = hairdresser.update(new HairdresserUpdateCommand(storeId)).toDto();
        hairdresserDao.save(uploadedHairdresserDto);
        log.info("Uploaded photo for hairdresser {uploadedHairdresserDto: {}}", uploadedHairdresserDto);
    }

    @Override
    public HairdresserDto findOne(String hairdresserId) {
        log.trace("Getting hairdresser {hairdresserId: {}}", hairdresserId);
        var hairdresserDto = getOneFromDaoOrThrow(hairdresserId);
        log.info("Got hairdresser {hairdresserDto: {}}", hairdresserDto);
        return hairdresserDto;
    }

    @Override
    public PageSlice<HairdresserDto> findAll(HairdresserFindQuery findQuery, PageableParamsQuery paramsQuery) {
        log.trace("Searching hairdressers {findQuery: {}, paramsQuery: {}}", findQuery, paramsQuery);
        var page = hairdresserDao.findAll(findQuery, paramsQuery);
        log.info("Searched hairdressers {contentTotalElements: {}, contentSize: {}}", page.getTotalElements(), page.getSize());
        return page;
    }

    private Hairdresser buildHairdresser(HairdresserCreateCommand command) {
        return Hairdresser.builder()
                .id(idGenerator.generate())
                .name(command.getName())
                .surname(command.getSurname())
                .services(command.getServices())
                .build();
    }

    private HairdresserDto getOneFromDaoOrThrow(String hairdresserId) {
        return hairdresserDao.findOne(HairdresserFindQuery.ofHairdresserId(hairdresserId))
                .orElseThrow(() -> new HairdresserNotFoundException(
                        format("Hairdresser not found by id: %s", hairdresserId))
                );
    }

}
