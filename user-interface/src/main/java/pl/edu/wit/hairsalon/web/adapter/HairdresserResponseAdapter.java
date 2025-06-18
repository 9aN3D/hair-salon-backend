package pl.edu.wit.hairsalon.web.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wit.hairsalon.hairdresser.HairdresserFacade;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.hairsalon.uploadableFile.UploadableFileFacade;
import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;
import pl.edu.wit.hairsalon.web.response.HairdresserResponse;

import static java.lang.String.format;

@Service
public class HairdresserResponseAdapter {

    private final HairdresserFacade hairdresserFacade;
    private final UploadableFileFacade uploadableFileFacade;

    public HairdresserResponseAdapter(HairdresserFacade hairdresserFacade,
                                      UploadableFileFacade uploadableFileFacade) {
        this.hairdresserFacade = hairdresserFacade;
        this.uploadableFileFacade = uploadableFileFacade;
    }

    public String create(HairdresserCreateCommand command) {
        return hairdresserFacade.create(command);
    }

    public void uploadPhoto(String hairdresserId, MultipartFile file) {
        hairdresserFacade.uploadPhoto(hairdresserId, buildFileUploadCommand(file));
    }

    public HairdresserResponse findOne(String hairdresserId) {
        return HairdresserResponse.of(hairdresserFacade.findOne(hairdresserId), uploadableFileFacade::findOne);
    }

    public Page<HairdresserResponse> findAll(HairdresserFindQuery findQuery, Pageable pageable) {
        return hairdresserFacade.findAll(findQuery, pageable)
                .map(hairdresserDto -> HairdresserResponse.of(hairdresserDto, uploadableFileFacade::findOne));
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
