package pl.edu.wit.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.application.command.FileUploadCommand;
import pl.edu.wit.application.command.hairdresser.HairdresserCreateCommand;
import pl.edu.wit.application.domain.model.hairdresser.Hairdresser;
import pl.edu.wit.application.domain.usecase.hairdresser.HairdresserUploadPhotoUseCase;
import pl.edu.wit.application.dto.HairdresserDto;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.exception.hairdresser.HairdresserNotFoundException;
import pl.edu.wit.application.port.primary.HairdresserService;
import pl.edu.wit.application.port.secondary.HairdresserDao;
import pl.edu.wit.application.port.secondary.IdGenerator;
import pl.edu.wit.application.query.HairdresserFindQuery;
import pl.edu.wit.application.query.PageableParamsQuery;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public class AppHairdresserService implements HairdresserService {

    private final IdGenerator idGenerator;
    private final HairdresserDao hairdresserDao;
    private final HairdresserUploadPhotoUseCase uploadPhotoUseCase;

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
        var uploadedHairdresserDto = uploadPhotoUseCase.execute(hairdresserDto, command);
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
