package pl.edu.wit.application.domain.usecase.hairdresser;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.application.command.FileUploadCommand;
import pl.edu.wit.application.command.hairdresser.HairdresserUpdateCommand;
import pl.edu.wit.application.domain.model.hairdresser.Hairdresser;
import pl.edu.wit.application.dto.HairdresserDto;
import pl.edu.wit.application.port.primary.UploadableFileService;

@RequiredArgsConstructor
public class DefaultHairdresserUploadPhotoUseCase implements HairdresserUploadPhotoUseCase {

    private final UploadableFileService uploadableFileService;

    @Override
    public HairdresserDto execute(HairdresserDto hairdresserDto, FileUploadCommand command) {
        var hairdresser = new Hairdresser(hairdresserDto);
        var storeId = uploadableFileService.store(command);
        if (hairdresser.hasPhotoId()) {
            uploadableFileService.delete(hairdresser.phoneId());
        }
        return hairdresser.update(new HairdresserUpdateCommand(storeId)).toDto();
    }

}
