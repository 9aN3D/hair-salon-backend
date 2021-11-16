package pl.edu.wit.hairsalon.web.adapter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.HairdresserFacade;
import pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.hairsalon.web.response.HairdresserResponse;
import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;
import pl.edu.wit.hairsalon.uploadableFile.UploadableFileFacade;

@Service
@RequiredArgsConstructor
public class HairdresserResponseAdapter {

    private final HairdresserFacade hairdresserFacade;
    private final UploadableFileFacade uploadableFileFacade;

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

    @SneakyThrows
    private FileUploadCommand buildFileUploadCommand(MultipartFile file) {
        return FileUploadCommand.builder()
                .originalFilename(file.getOriginalFilename())
                .contentType(file.getContentType())
                .size(file.getSize())
                .content(file.getInputStream())
                .build();
    }

}
