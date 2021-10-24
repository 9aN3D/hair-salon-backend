package pl.edu.wit.application.domain.usecase.hairdresser;

import pl.edu.wit.application.command.FileUploadCommand;
import pl.edu.wit.application.dto.HairdresserDto;

public interface HairdresserUploadPhotoUseCase {

    HairdresserDto execute(HairdresserDto hairdresserDto, FileUploadCommand command);

}
