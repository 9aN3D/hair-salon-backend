package pl.edu.wit.api.facade;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wit.api.mapper.PageableParamsMapper;
import pl.edu.wit.api.response.HairdresserResponse;
import pl.edu.wit.application.command.FileUploadCommand;
import pl.edu.wit.application.command.hairdresser.HairdresserCreateCommand;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.port.primary.HairdresserService;
import pl.edu.wit.application.port.primary.UploadableFileService;
import pl.edu.wit.application.query.HairdresserFindQuery;

@Service
@RequiredArgsConstructor
public class DefaultHairdresserFacade implements HairdresserFacade {

    private final HairdresserService hairdresserService;
    private final UploadableFileService uploadableFileService;
    private final PageableParamsMapper pageableParamsMapper;

    @Override
    public String create(HairdresserCreateCommand command) {
        return hairdresserService.create(command);
    }

    @Override
    public void uploadPhoto(String hairdresserId, MultipartFile file) {
        hairdresserService.uploadPhoto(hairdresserId, buildFileUploadCommand(file));
    }

    @Override
    public HairdresserResponse findOne(String hairdresserId) {
        return HairdresserResponse.of(hairdresserService.findOne(hairdresserId), uploadableFileService::findOne);
    }

    @Override
    public PageSlice<HairdresserResponse> findAll(HairdresserFindQuery findQuery, Pageable pageable) {
        return hairdresserService.findAll(findQuery, pageableParamsMapper.toPageableParamsQuery(pageable))
                .map(hairdresserDto -> HairdresserResponse.of(hairdresserDto, uploadableFileService::findOne));
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
