package pl.edu.wit.hairsalon.web.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wit.hairsalon.hairdresser.HairdresserDayOverrideFacade;
import pl.edu.wit.hairsalon.hairdresser.HairdresserFacade;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserDayOverrideCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserDayOverrideUpdateCommand;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserUpdateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideIdDto;
import pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.hairsalon.uploadableFile.UploadableFileFacade;
import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;
import pl.edu.wit.hairsalon.web.response.HairdresserResponse;
import pl.edu.wit.hairsalon.web.response.PagedResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.lang.String.format;

@Service
public class HairdresserResponseAdapter {

    private final HairdresserFacade hairdresserFacade;
    private final UploadableFileFacade uploadableFileFacade;
    private final HairdresserDayOverrideFacade hairdresserDayOverrideFacade;

    public HairdresserResponseAdapter(HairdresserFacade hairdresserFacade,
                                      UploadableFileFacade uploadableFileFacade,
                                      HairdresserDayOverrideFacade hairdresserDayOverrideFacade) {
        this.hairdresserFacade = hairdresserFacade;
        this.uploadableFileFacade = uploadableFileFacade;
        this.hairdresserDayOverrideFacade = hairdresserDayOverrideFacade;
    }

    public String create(HairdresserCreateCommand command) {
        return hairdresserFacade.create(command);
    }

    public void uploadPhoto(String hairdresserId, MultipartFile file) {
        hairdresserFacade.uploadPhoto(hairdresserId, buildFileUploadCommand(file));
    }

    public void update(String hairdresserId, HairdresserUpdateCommand command) {
        hairdresserFacade.update(hairdresserId, command);
    }

    public HairdresserResponse findOne(String hairdresserId) {
        return HairdresserResponse.of(hairdresserFacade.findOne(hairdresserId), uploadableFileFacade::findOne);
    }

    public PagedResponse<HairdresserResponse> findAll(HairdresserFindQuery findQuery, Pageable pageable) {
        return PagedResponse.from(hairdresserFacade.findAll(findQuery, pageable)
                .map(hairdresserDto -> HairdresserResponse.of(hairdresserDto, uploadableFileFacade::findOne))
        );
    }

    public List<LocalTime> getAvailableStartTimes(String hairdresserId, LocalDate date) {
        return hairdresserFacade.getAvailableStartTimes(hairdresserId, date);
    }

    public HairdresserDayOverrideIdDto createDayOverride(HairdresserDayOverrideCreateCommand command) {
        return hairdresserDayOverrideFacade.create(command);
    }

    public void updateDayOverride(HairdresserDayOverrideIdDto id, HairdresserDayOverrideUpdateCommand command) {
        hairdresserDayOverrideFacade.update(id, command);
    }

    private FileUploadCommand buildFileUploadCommand(MultipartFile file) {
        try (var content = file.getInputStream()) {
            return FileUploadCommand.builder()
                    .originalFilename(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .content(content)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(
                    format("Cant build file uploadCommand, message: %s", e.getMessage()), e
            );
        }
    }

}
